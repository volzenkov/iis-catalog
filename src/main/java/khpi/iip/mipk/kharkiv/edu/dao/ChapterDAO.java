package khpi.iip.mipk.kharkiv.edu.dao;

import khpi.iip.mipk.kharkiv.edu.domain.Chapter;

import java.util.List;

public interface ChapterDAO extends GenericDAO<Chapter>{

    public List<Chapter> listRootChapters(boolean isGuest);

}