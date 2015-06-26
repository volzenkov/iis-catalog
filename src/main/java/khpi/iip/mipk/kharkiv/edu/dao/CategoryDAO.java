package khpi.iip.mipk.kharkiv.edu.dao;

import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Chapter;

import java.util.List;

public interface CategoryDAO extends GenericDAO<Category>{

    public List<Category> listCategories(Long chapterId, boolean isGuest);

}