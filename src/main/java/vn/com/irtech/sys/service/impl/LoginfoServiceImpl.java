package vn.com.irtech.sys.service.impl;

import vn.com.irtech.sys.entity.Loginfo;
import vn.com.irtech.sys.mapper.LoginfoMapper;
import vn.com.irtech.sys.service.ILoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author:  Admin
 * @since 2019-11-23
 */
@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements ILoginfoService {

}
