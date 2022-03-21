package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VTS.
 */
@Entity
@Table(name = "vts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VTS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "verkehrstage")
    private String verkehrstage;

    @Column(name = "beschreibung")
    private String beschreibung;

    @Column(name = "versionsnummer")
    private String versionsnummer;

    @Column(name = "mandant")
    private String mandant;

    @Column(name = "feiertage")
    private String feiertage;

    @Column(name = "gueltigkeit")
    private String gueltigkeit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VTS id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerkehrstage() {
        return this.verkehrstage;
    }

    public VTS verkehrstage(String verkehrstage) {
        this.setVerkehrstage(verkehrstage);
        return this;
    }

    public void setVerkehrstage(String verkehrstage) {
        this.verkehrstage = verkehrstage;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public VTS beschreibung(String beschreibung) {
        this.setBeschreibung(beschreibung);
        return this;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getVersionsnummer() {
        return this.versionsnummer;
    }

    public VTS versionsnummer(String versionsnummer) {
        this.setVersionsnummer(versionsnummer);
        return this;
    }

    public void setVersionsnummer(String versionsnummer) {
        this.versionsnummer = versionsnummer;
    }

    public String getMandant() {
        return this.mandant;
    }

    public VTS mandant(String mandant) {
        this.setMandant(mandant);
        return this;
    }

    public void setMandant(String mandant) {
        this.mandant = mandant;
    }

    public String getFeiertage() {
        return this.feiertage;
    }

    public VTS feiertage(String feiertage) {
        this.setFeiertage(feiertage);
        return this;
    }

    public void setFeiertage(String feiertage) {
        this.feiertage = feiertage;
    }

    public String getGueltigkeit() {
        return this.gueltigkeit;
    }

    public VTS gueltigkeit(String gueltigkeit) {
        this.setGueltigkeit(gueltigkeit);
        return this;
    }

    public void setGueltigkeit(String gueltigkeit) {
        this.gueltigkeit = gueltigkeit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VTS)) {
            return false;
        }
        return id != null && id.equals(((VTS) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VTS{" +
            "id=" + getId() +
            ", verkehrstage='" + getVerkehrstage() + "'" +
            ", beschreibung='" + getBeschreibung() + "'" +
            ", versionsnummer='" + getVersionsnummer() + "'" +
            ", mandant='" + getMandant() + "'" +
            ", feiertage='" + getFeiertage() + "'" +
            ", gueltigkeit='" + getGueltigkeit() + "'" +
            "}";
    }
}
