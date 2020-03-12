package vn.com.irtech.sys.service.impl;

import vn.com.irtech.sys.entity.Notice;
import vn.com.irtech.sys.mapper.NoticeMapper;
import vn.com.irtech.sys.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author:  Admin
 * @since 2019-11-25
 */
@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
