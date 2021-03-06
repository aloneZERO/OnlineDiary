package com.leo.apediary.service.impl;

import java.util.List;

import com.leo.apediary.common.Page;
import com.leo.apediary.dao.CategoryDAO;
import com.leo.apediary.dao.DiaryDAO;
import com.leo.apediary.dao.UserDAO;
import com.leo.apediary.dao.impl.CategoryDAOImpl;
import com.leo.apediary.dao.impl.DiaryDAOImpl;
import com.leo.apediary.dao.impl.UserDAOImpl;
import com.leo.apediary.domain.Category;
import com.leo.apediary.domain.Diary;
import com.leo.apediary.domain.User;
import com.leo.apediary.service.BusinessService;
import com.leo.apediary.util.IdGenertor;
import com.leo.apediary.web.bean.CategoryInfoBean;
import com.leo.apediary.web.bean.DiarySearchBean;
import com.mysql.jdbc.StringUtils;

public class BusinessServiceImpl implements BusinessService {
	
	private UserDAO userDAO = new UserDAOImpl();
	private CategoryDAO categoryDAO = new CategoryDAOImpl();
	private DiaryDAO diaryDAO = new DiaryDAOImpl();
	
	@Override
	public User login(String username, String password) {
		return userDAO.find(username, password);
	}
	@Override
	public int userUpate(User user) {
		return userDAO.update(user);
	}

	@Override
	public int addCategory(Category c) {
		c.setId(IdGenertor.genGUID());
		return categoryDAO.save(c);
	}

	@Override
	public List<Category> findAllCategories() {
		return categoryDAO.findAll();
	}

	@Override
	public Category findCategoryById(String categoryId) {
		return categoryDAO.findById(categoryId);
	}

	@Override
	public int delCategory(String categoryId) {
		return categoryDAO.del(categoryId);
	}

	@Override
	public int diaryCountByCategoryId(String categoryId) {
		return categoryDAO.diaryCount(categoryId);
	}
	@Override
	public boolean existDiary(String categoryId) {
		int num = this.diaryCountByCategoryId(categoryId);
		if(num > 0) return true;
		else return false;
	}

	@Override
	public int updateCategory(Category c) {
		return categoryDAO.update(c);
	}
	@Override
	public int addDiary(Diary diary) {
		if(diary == null) {
			throw new IllegalArgumentException("The apediary can not be null");
		}
		if(diary.getCategory() == null) {
			throw new IllegalArgumentException("The apediary's category can not be null");
		}
		diary.setId(IdGenertor.genGUID());
		return diaryDAO.save(diary);
	}
	@Override
	public int delDiary(String diaryId) {
		return diaryDAO.del(diaryId);
	}
	@Override
	public int updateDiary(Diary diary) {
		return diaryDAO.update(diary);
	}
	@Override
	public Diary findDiaryById(String diaryId) {
		return diaryDAO.findById(diaryId);
	}
	
	@Override
	public Page findDiaryPageRecords(String num, DiarySearchBean diarySearchBean) {
		int pageNum = 1;
		if (!StringUtils.isNullOrEmpty(num)) {
			pageNum = Integer.parseInt(num);
		}
		int totalRecordsNum = diaryDAO.getTotalRecordsNum(diarySearchBean);
		Page page = new Page(pageNum, totalRecordsNum);
		List<DiarySearchBean> records = diaryDAO.findPageRecords(
				page.getStartIndex(), page.getPageSize(), diarySearchBean);
		page.setRecords(records);
		return page;
	}
	@Override
	public List<DiarySearchBean> findDiaryInfoByReleaseDate() {
		return diaryDAO.findInfoByReleaseDate();
	}
	@Override
	public List<CategoryInfoBean> findDiayInfoByCategory() {
		return categoryDAO.findDiaryInfo();
	}
	
}
