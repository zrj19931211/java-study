package zrj.study.zzone.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zrj.study.zzone.core.dao.SessionDao;
import zrj.study.zzone.core.entity.Session;
import zrj.study.zzone.core.entity.User;

import java.util.Date;
import java.util.UUID;

/**
 * @author zhongrj
 * @email 329053269@qq.com
 * @date 2017/4/20
 */
@Service
@Transactional(readOnly = true)
public class SessionService extends BaseService {

    private static final long ONE_WEEK = 1L * 7 * 24 * 60 * 60 * 1000;

    @Autowired
    private SessionDao sessionDao;

    @Transactional
    public String addSession(User user) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        Session session = new Session();
        session.preInsert();
        session.setToken(token);
        session.setUserId(user.getId());
        session.setExpireDate(new Date(System.currentTimeMillis() + ONE_WEEK));
        sessionDao.insert(session);
        return token;
    }

    public User getUserByToken(String token) {
        return sessionDao.getUserByToken(token);
    }
}