package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SpecialAnnouncement.
 */
@Entity
@Table(name = "special_announcement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SpecialAnnouncement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "item")
    private String item;

    @Column(name = "ausgabe_ort")
    private String ausgabeOrt;

    @Column(name = "kurz")
    private String kurz;

    @Column(name = "language")
    private String language;

    @Column(name = "ansagedatei")
    private String ansagedatei;

    @Column(name = "klartext")
    private String klartext;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SpecialAnnouncement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public SpecialAnnouncement code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItem() {
        return this.item;
    }

    public SpecialAnnouncement item(String item) {
        this.setItem(item);
        return this;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAusgabeOrt() {
        return this.ausgabeOrt;
    }

    public SpecialAnnouncement ausgabeOrt(String ausgabeOrt) {
        this.setAusgabeOrt(ausgabeOrt);
        return this;
    }

    public void setAusgabeOrt(String ausgabeOrt) {
        this.ausgabeOrt = ausgabeOrt;
    }

    public String getKurz() {
        return this.kurz;
    }

    public SpecialAnnouncement kurz(String kurz) {
        this.setKurz(kurz);
        return this;
    }

    public void setKurz(String kurz) {
        this.kurz = kurz;
    }

    public String getLanguage() {
        return this.language;
    }

    public SpecialAnnouncement language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAnsagedatei() {
        return this.ansagedatei;
    }

    public SpecialAnnouncement ansagedatei(String ansagedatei) {
        this.setAnsagedatei(ansagedatei);
        return this;
    }

    public void setAnsagedatei(String ansagedatei) {
        this.ansagedatei = ansagedatei;
    }

    public String getKlartext() {
        return this.klartext;
    }

    public SpecialAnnouncement klartext(String klartext) {
        this.setKlartext(klartext);
        return this;
    }

    public void setKlartext(String klartext) {
        this.klartext = klartext;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecialAnnouncement)) {
            return false;
        }
        return id != null && id.equals(((SpecialAnnouncement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecialAnnouncement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", item='" + getItem() + "'" +
            ", ausgabeOrt='" + getAusgabeOrt() + "'" +
            ", kurz='" + getKurz() + "'" +
            ", language='" + getLanguage() + "'" +
            ", ansagedatei='" + getAnsagedatei() + "'" +
            ", klartext='" + getKlartext() + "'" +
            "}";
    }
}
