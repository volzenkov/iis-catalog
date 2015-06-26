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
@Table(name = "document")
@PrimaryKeyJoinColumn(name = "catalog_item_id")
public class Document extends CatalogItem {

    @Column
    private String link;

    @Column
    private String description;

    public Document() {
        this.setCatalogItemType(CatalogItemType.DOCUMENT);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
