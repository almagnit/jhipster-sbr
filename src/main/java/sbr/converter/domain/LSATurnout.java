package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LSATurnout.
 */
@Entity
@Table(name = "lsa_turnout")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LSATurnout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "station_1")
    private String station1;

    @Column(name = "station_2")
    private String station2;

    @Column(name = "lsa_code")
    private String lsaCode;

    @Column(name = "turnout")
    private String turnout;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LSATurnout id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStation1() {
        return this.station1;
    }

    public LSATurnout station1(String station1) {
        this.setStation1(station1);
        return this;
    }

    public void setStation1(String station1) {
        this.station1 = station1;
    }

    public String getStation2() {
        return this.station2;
    }

    public LSATurnout station2(String station2) {
        this.setStation2(station2);
        return this;
    }

    public void setStation2(String station2) {
        this.station2 = station2;
    }

    public String getLsaCode() {
        return this.lsaCode;
    }

    public LSATurnout lsaCode(String lsaCode) {
        this.setLsaCode(lsaCode);
        return this;
    }

    public void setLsaCode(String lsaCode) {
        this.lsaCode = lsaCode;
    }

    public String getTurnout() {
        return this.turnout;
    }

    public LSATurnout turnout(String turnout) {
        this.setTurnout(turnout);
        return this;
    }

    public void setTurnout(String turnout) {
        this.turnout = turnout;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LSATurnout)) {
            return false;
        }
        return id != null && id.equals(((LSATurnout) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LSATurnout{" +
            "id=" + getId() +
            ", station1='" + getStation1() + "'" +
            ", station2='" + getStation2() + "'" +
            ", lsaCode='" + getLsaCode() + "'" +
            ", turnout='" + getTurnout() + "'" +
            "}";
    }
}
