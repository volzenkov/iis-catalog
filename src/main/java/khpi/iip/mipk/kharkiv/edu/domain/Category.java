package khpi.iip.mipk.kharkiv.edu.domain;

import khpi.iip.mipk.kharkiv.edu.domain.enums.CatalogItemType;
import khpi.iip.mipk.kharkiv.edu.domain.enums.CategoryType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author: vzenkov
 */
@Entity
@Table(name = "category")
@PrimaryKeyJoinColumn(name = "catalog_item_id")
public class Category extends CatalogItem {

    @Column
    private CategoryType categoryType;

    public Category() {
        this.setCatalogItemType(CatalogItemType.CATEGORY);
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
