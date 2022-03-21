package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GPS.
 */
@Entity
@Table(name = "gps")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GPS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gps_code")
    private String gpsCode;

    @Column(name = "inneres_fenster")
    private String inneresFenster;

    @Column(name = "au_beres_fenster")
    private String auBeresFenster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GPS id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public GPS name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGpsCode() {
        return this.gpsCode;
    }

    public GPS gpsCode(String gpsCode) {
        this.setGpsCode(gpsCode);
        return this;
    }

    public void setGpsCode(String gpsCode) {
        this.gpsCode = gpsCode;
    }

    public String getInneresFenster() {
        return this.inneresFenster;
    }

    public GPS inneresFenster(String inneresFenster) {
        this.setInneresFenster(inneresFenster);
        return this;
    }

    public void setInneresFenster(String inneresFenster) {
        this.inneresFenster = inneresFenster;
    }

    public String getAuBeresFenster() {
        return this.auBeresFenster;
    }

    public GPS auBeresFenster(String auBeresFenster) {
        this.setAuBeresFenster(auBeresFenster);
        return this;
    }

    public void setAuBeresFenster(String auBeresFenster) {
        this.auBeresFenster = auBeresFenster;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GPS)) {
            return false;
        }
        return id != null && id.equals(((GPS) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GPS{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gpsCode='" + getGpsCode() + "'" +
            ", inneresFenster='" + getInneresFenster() + "'" +
            ", auBeresFenster='" + getAuBeresFenster() + "'" +
            "}";
    }
}
