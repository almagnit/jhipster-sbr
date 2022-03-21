package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Announcement.
 */
@Entity
@Table(name = "announcement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "ansage")
    private String ansage;

    @Column(name = "item")
    private String item;

    @Column(name = "announcementfile")
    private String announcementfile;

    @Column(name = "klartext")
    private String klartext;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Announcement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Announcement code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAnsage() {
        return this.ansage;
    }

    public Announcement ansage(String ansage) {
        this.setAnsage(ansage);
        return this;
    }

    public void setAnsage(String ansage) {
        this.ansage = ansage;
    }

    public String getItem() {
        return this.item;
    }

    public Announcement item(String item) {
        this.setItem(item);
        return this;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAnnouncementfile() {
        return this.announcementfile;
    }

    public Announcement announcementfile(String announcementfile) {
        this.setAnnouncementfile(announcementfile);
        return this;
    }

    public void setAnnouncementfile(String announcementfile) {
        this.announcementfile = announcementfile;
    }

    public String getKlartext() {
        return this.klartext;
    }

    public Announcement klartext(String klartext) {
        this.setKlartext(klartext);
        return this;
    }

    public void setKlartext(String klartext) {
        this.klartext = klartext;
    }

    public String getLanguage() {
        return this.language;
    }

    public Announcement language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Announcement)) {
            return false;
        }
        return id != null && id.equals(((Announcement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Announcement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", ansage='" + getAnsage() + "'" +
            ", item='" + getItem() + "'" +
            ", announcementfile='" + getAnnouncementfile() + "'" +
            ", klartext='" + getKlartext() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
