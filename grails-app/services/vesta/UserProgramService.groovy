package vesta

import grails.gorm.services.Service

@Service(UserProgram)
interface UserProgramService {

    UserProgram get(Serializable id)

    List<UserProgram> list(Map args)

    Long count()

    void delete(Serializable id)

    UserProgram save(UserProgram userProgram)

}