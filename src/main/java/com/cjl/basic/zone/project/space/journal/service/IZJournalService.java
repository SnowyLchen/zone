package com.cjl.basic.zone.project.space.journal.service;

import com.cjl.basic.zone.project.space.journal.domain.ZJournal;

import java.util.List;

/**
 * 日志模块业务层
 */
public interface IZJournalService {
    /**
     * 删除某篇日志
     *
     * @param jId
     * @return
     */
    int deleteJournalById(Integer jId);

    /**
     * 删除多篇日志
     *
     * @param ids
     * @return
     */
    int deleteJournalByIds(String ids);

    /**
     * 清空用户日志
     *
     * @param accountId
     * @return
     */
    int deleteJournalByAccountId(Integer accountId);

    /**
     * 写日志
     *
     * @param zJournal
     * @return
     */
    int addJournal(ZJournal zJournal);

    /**
     * 查询单个日志
     *
     * @param jId
     * @return
     */
    ZJournal selectJournalById(Integer jId);

    /**
     * 查询某个用户日志
     *
     * @param accountId
     * @return
     */
    List<ZJournal> selectJournalListByAccountId(ZJournal journal);

    /**
     * 更新日志
     *
     * @param record
     * @return
     */
    int updateJournalById(ZJournal record);
}
