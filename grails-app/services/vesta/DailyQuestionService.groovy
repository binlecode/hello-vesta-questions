package vesta

import grails.gorm.transactions.NotTransactional
import grails.gorm.transactions.Transactional

/**
 * for following business needs:
 * - one user can get up to 6 questions for a given day
 * - one user can request questions multiple times for a given day
 * - questions provided each time should not overlap with previously given questions
 * -
 */
@Transactional
class DailyQuestionService {

    static final int QUESTION_BATCH_MAX = 6

    /*
     * since questions are drawn from pool with time boundary of 'day', we can setup a question cache
     * with TTL of day, say 12:00AM system time, or: each individual question has a TTL of 24 hours and set a
     * house-keeping job running every 10min to clean old questions
     *
     * also, it is reasonable (or not?) to assume there's a large pool of questions per program, then it is
     * preferred to fetch questions with historical question collission avoidance at data side
     *
     *
     */
    def getDailyQuestions(User user) {

        // get candidate questions for given user via (multiple) programs
        List userProgramIds = UserProgram.findAllByUser(user)*.programId
        def questionCriteria = Question.where {
            program.id in userProgramIds
        }
        List<Question> questionCandidatesForToday = questionCriteria.list()
//        Number questionCandidatesCountForToday = questionCriteria.count()

        // remove dups from today's history
        List<Question> alreadyGivenQuestionsForToday = getQuestionsGivenToday(user)
        questionCandidatesForToday.removeIf { (alreadyGivenQuestionsForToday*.id).contains(it.id) }

        // randomize question candidate list
        Collections.shuffle(questionCandidatesForToday)
        // then take up to QUESTION_BATCH_MAX
        List<Question> dailyQuestions = questionCandidatesForToday.take(QUESTION_BATCH_MAX)

        return dailyQuestions
    }

    /**
     * get all questions that have been fetched since the beginning of today
     */
    List<Question> getQuestionsGivenToday(User user) {
        UserQuestionHistory.where {
            user == user
            dateCreated > getStartOfDay(new Date())
        }.question.list()
    }

    /**
     * get midnight timestamp for a given day
     */
    @NotTransactional
    Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.getTime()
    }


}
