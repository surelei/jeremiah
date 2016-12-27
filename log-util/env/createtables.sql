DROP TABLE t_log_report;
CREATE TABLE t_log_report (
	log_id INT,
	log_url VARCHAR(2000),
	log_user_num INT,
	log_page_view INT,
	log_year INT,
	log_month INT,
	log_day INT,
	log_hour INT,
	PRIMARY KEY (log_id)
);