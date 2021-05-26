package com.cjl.basic.zone.project.space.journal.mapper;

import com.cjl.basic.zone.project.space.journal.domain.ZJournal;

import java.util.List;

public interface ZJournalMapper {
    /**
     * 删除某篇日志
     *
     * @param jId
     * @return
     */
    int deleteJournalById(Integer jId);

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
     * @param journal
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
