package com.jeremiahxu.util.log.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.jeremiahxu.util.log.LogReportRecord;

/**
 * apache的common类型日志sqlmap
 * 
 * @author Jeremiah Xu
 *
 */
public interface LogMapper {
	@Insert("insert into t_log_report(log_id,log_url,log_user_num,log_page_view,log_year,log_month,log_day,log_hour) "
			+ "VALUES(#{id},#{url},#{userNumber},#{pageView},#{year},#{month},#{day},#{hour})")
	@SelectKey(before = true, keyProperty = "id", resultType = Integer.class, statement = { "select ifnull(max(log_id),0)+1 from t_log_report" })
	public int insertPageView(LogReportRecord record);
}
