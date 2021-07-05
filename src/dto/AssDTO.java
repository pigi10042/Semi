package dto;

import java.sql.Date;

public class AssDTO {
	//	create table ASS(
	//		    seq number primary key,
	//		    writer varchar2(15) not null,
	//		    id varchar2(20) not null,
	//		    title varchar2(150) not null,
	//		    contents varchar2(4000) not null,
	//			khClass varchar2(10) not null,
	//			branch varchar2(10) not null,
	//		    write_date date default sysdate not null,
	//		    viewCount number default 0 not null
	//		); 이거 누가 써줬어? 천사야...

	private int seq;
	private String writer;
	private String id;
	private String title;
	private String contents;
	private String khClass;
	private String branch;
	private Date write_date;
	private int viewCount;

	public AssDTO() {}

	public AssDTO(int seq, String writer, String id, String title, String contents, String khClass, String branch,
			Date write_date, int viewCount) {
		
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.khClass = khClass;
		this.branch = branch;
		this.write_date = write_date;
		this.viewCount = viewCount;
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getKhClass() {
		return khClass;
	}

	public void setKhClass(String khClass) {
		this.khClass = khClass;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}


}
