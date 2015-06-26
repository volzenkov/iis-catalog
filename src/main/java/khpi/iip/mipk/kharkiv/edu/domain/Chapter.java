package khpi.iip.mipk.kharkiv.edu.domain;

import khpi.iip.mipk.kharkiv.edu.domain.enums.CatalogItemType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author: vzenkov
 */
@Entity
@Table(name = "chapter")
@PrimaryKeyJoinColumn(name = "catalog_item_id")
public class Chapter extends CatalogItem {

    @Column
    private String description;

    public Chapter() {
        this.setCatalogItemType(CatalogItemType.CHAPTER);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
