package com.cjl.basic.zone.project.space.journal.service;

import com.cjl.basic.zone.common.support.Convert;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.project.space.journal.domain.ZJournal;
import com.cjl.basic.zone.project.space.journal.mapper.ZJournalMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志模块业务层
 */
@Service
public class ZJournalServiceImpl implements IZJournalService {
    @Resource
    private ZJournalMapper journalMapper;

    @Override
    public int deleteJournalById(Integer jId) {
        return journalMapper.deleteJournalById(jId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteJournalByIds(String ids) {
        for (Integer id : Convert.toIntArray(ids)) {
            deleteJournalById(id);
        }
        return 1;
    }

    @Override
    public int deleteJournalByAccountId(Integer accountId) {
        return journalMapper.deleteJournalByAccountId(accountId);
    }

    @Override
    public int addJournal(ZJournal zJournal) {
        InsertOrUpdateUtils.addInsertAttr(zJournal);
        return journalMapper.addJournal(zJournal);
    }

    @Override
    public ZJournal selectJournalById(Integer jId) {
        return journalMapper.selectJournalById(jId);
    }

    @Override
    public List<ZJournal> selectJournalListByAccountId(ZJournal journal) {
        return journalMapper.selectJournalListByAccountId(journal);
    }

    @Override
    public int updateJournalById(ZJournal zJournal) {
        InsertOrUpdateUtils.addUpdateAttr(zJournal);
        return journalMapper.updateJournalById(zJournal);
    }
}
