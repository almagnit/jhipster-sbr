package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FunctionAnnouncement.
 */
@Entity
@Table(name = "function_announcement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FunctionAnnouncement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "audio_file")
    private String audioFile;

    @Column(name = "beschreibung")
    private String beschreibung;

    @Column(name = "anmerkung")
    private String anmerkung;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FunctionAnnouncement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public FunctionAnnouncement code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAudioFile() {
        return this.audioFile;
    }

    public FunctionAnnouncement audioFile(String audioFile) {
        this.setAudioFile(audioFile);
        return this;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public FunctionAnnouncement beschreibung(String beschreibung) {
        this.setBeschreibung(beschreibung);
        return this;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getAnmerkung() {
        return this.anmerkung;
    }

    public FunctionAnnouncement anmerkung(String anmerkung) {
        this.setAnmerkung(anmerkung);
        return this;
    }

    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    public String getLanguage() {
        return this.language;
    }

    public FunctionAnnouncement language(String language) {
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
        if (!(o instanceof FunctionAnnouncement)) {
            return false;
        }
        return id != null && id.equals(((FunctionAnnouncement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FunctionAnnouncement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", audioFile='" + getAudioFile() + "'" +
            ", beschreibung='" + getBeschreibung() + "'" +
            ", anmerkung='" + getAnmerkung() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
