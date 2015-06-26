package khpi.iip.mipk.kharkiv.edu.web;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Chapter;
import khpi.iip.mipk.kharkiv.edu.domain.Document;
import khpi.iip.mipk.kharkiv.edu.service.CatalogItemService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @autor vzenkov
 */

@Component
@ManagedBean(name = "documentController")
@SessionScoped
public class DocumentController {

    private Category parentCategory;
    private Chapter parentChapter;
    private List<Document> documentsList = new ArrayList<Document>();

    //    @ManagedProperty(value = "#{UserService}")
    @Autowired
    CatalogItemService catalogItemService;

    @Autowired
    UserBean userBean;

    private Document documentForEdit = new Document();
    private Document newDocument = new Document();

    public void refreshDocumentsList() {
        documentsList = catalogItemService.listDocuments(parentCategory.getItemId(), userBean.getCurrentUser() == null);
    }

    public void initNewDocumentDialog() {
        newDocument = new Document();
        newDocument.setParent(parentCategory);
    }

    public void initEditDocumentDialog(Document document) {
        documentForEdit = document;
    }

    public String addDocument() {
        catalogItemService.addCatalogItem(newDocument);
        refreshDocumentsList();
        return "toDocuments";
    }

    public void handleFileUpload(FileUploadEvent event) {

        newDocument.setLink(null);

        UploadedFile eventFile = event.getFile();

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/documents/");
        SimpleDateFormat fmt = new SimpleDateFormat("-dd-MM-yyyy-HH-mm-ss");
        int fileNameSeparator = eventFile.getFileName().lastIndexOf('.');
        String fileName = eventFile.getFileName().substring(0, fileNameSeparator);
        String fileExtension = eventFile.getFileName().substring(fileNameSeparator);
        String name = fileName + fmt.format(new Date()) + fileExtension;
        String pathToSave = path + File.separator + name;
        File file = new File(pathToSave);

        OutputStream out = null;
        InputStream is = null;
        try {
            is = eventFile.getInputstream();
            out = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0)
                out.write(buf, 0, len);

            newDocument.setLink(name);

            FacesMessage msg = new FacesMessage("Файл загужен", eventFile.getFileName() + " успешно загружен.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            FacesMessage msg = new FacesMessage("Ошибка", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } finally {
            try {
                out.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    public StreamedContent getDownloadLink(String filePath) {
        InputStream is = null;

        try {
            int fileNameSeparator = filePath.lastIndexOf('.');
            int fileFolderSeparator = filePath.lastIndexOf(File.separator) + 1;
            String fileName = filePath.substring(fileFolderSeparator);
            String fileExtension = filePath.substring(fileNameSeparator);


            is = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext())
                    .getResourceAsStream("/documents/" + filePath);
//            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/" + filePath);

//            is = new FileInputStream(path);

            String contentType;
            if (fileExtension.equals(".jpg")) contentType = "image/jpg";
            else if (fileExtension.equals(".jpeg")) contentType = "image/jpeg";
            else if (fileExtension.equals(".doc")) contentType = "application/msword";
            else if (fileExtension.equals(".docx")) contentType = "application/msword";
            else if (fileExtension.equals(".txt")) contentType = "text/plain";
            else if (fileExtension.equals(".pdf")) contentType = "application/pdf";
            else if (fileExtension.equals(".zip")) contentType = "application/zip";
            else if (fileExtension.equals(".rar")) contentType = "application/rar";
            else return null;
            return new DefaultStreamedContent(is, contentType, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }
        return null;
    }

    public String updateDocument() {
        try {
            catalogItemService.updateCatalogItem(documentForEdit);
            refreshDocumentsList();
            documentForEdit = new Document();
            return "toDocuments";
        } catch (UpdateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public String removeDocument(Document document) {
        try {
            String link = document.getLink();
            catalogItemService.removeCatalogItem(document.getItemId());
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/documents/");
            String pathToSave = path + File.separator + link;
            File file = new File(pathToSave);
            if (file.exists()) {
                file.delete();
            }
            refreshDocumentsList();
            return "toDocuments";
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Ошибка", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Chapter getParentChapter() {
        return parentChapter;
    }

    public void setParentChapter(Chapter parentChapter) {
        this.parentChapter = parentChapter;
    }

    public List<Document> getDocumentsList() {
        return documentsList;
    }

    public void setDocumentsList(List<Document> documentsList) {
        this.documentsList = documentsList;
    }

    public Document getDocumentForEdit() {
        return documentForEdit;
    }

    public void setDocumentForEdit(Document documentForEdit) {
        this.documentForEdit = documentForEdit;
    }

    public Document getNewDocument() {
        return newDocument;
    }

    public void setNewDocument(Document newDocument) {
        this.newDocument = newDocument;
    }
}
