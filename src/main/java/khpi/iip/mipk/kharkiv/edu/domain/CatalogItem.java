package khpi.iip.mipk.kharkiv.edu.domain;

import khpi.iip.mipk.kharkiv.edu.domain.enums.CatalogItemType;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "getCatalogItemsByParentId", query = "from CatalogItem item where item.parent.itemId = :parentId"),
        @NamedQuery(name = "getRootCatalogItems", query = "from CatalogItem item where item.parent = null"),
        @NamedQuery(name = "getCatalogItemsByParentIdForGuest", query = "from CatalogItem item where item.parent.itemId = :parentId and item.hidden = false"),
        @NamedQuery(name = "getRootCatalogItemsForGuest", query = "from CatalogItem item where item.parent = null and item.hidden = false"),
        @NamedQuery(name = "getCatalogItemsByEditorId", query = "select item from CatalogItem item inner join item.editors editor where editor.userId = :editorId")
})

@Entity
@Table(name = "catalog_item")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CatalogItem extends PersistentEntity {

    @Id
    @GeneratedValue
    @Column(name = "catalog_item_id")
    private Long itemId;

    @Column(name = "catalog_item_name")
    private String name;

    @Column(name = "catalog_item_type")
    private CatalogItemType catalogItemType;

    @Column(name = "catalog_item_is_hidden", nullable = false)
    private Boolean hidden;

    @OneToOne(optional = true)
    private CatalogItem parent;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> editors;

    public CatalogItem getParent() {
        return parent;
    }

    public void setParent(CatalogItem parent) {
        this.parent = parent;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CatalogItemType getCatalogItemType() {
        return catalogItemType;
    }

    public void setCatalogItemType(CatalogItemType catalogItemType) {
        this.catalogItemType = catalogItemType;
    }

    public List<User> getEditors() {
        return editors;
    }

    public void setEditors(List<User> editors) {
        this.editors = editors;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
