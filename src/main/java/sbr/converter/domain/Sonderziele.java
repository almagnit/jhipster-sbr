package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sonderziele.
 */
@Entity
@Table(name = "sonderziele")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sonderziele implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "zugnummer")
    private String zugnummer;

    @Column(name = "front")
    private String front;

    @Column(name = "seite_1")
    private String seite1;

    @Column(name = "seite_2")
    private String seite2;

    @Column(name = "innen")
    private String innen;

    @Column(name = "tft")
    private String tft;

    @Column(name = "terminal")
    private String terminal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sonderziele id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZugnummer() {
        return this.zugnummer;
    }

    public Sonderziele zugnummer(String zugnummer) {
        this.setZugnummer(zugnummer);
        return this;
    }

    public void setZugnummer(String zugnummer) {
        this.zugnummer = zugnummer;
    }

    public String getFront() {
        return this.front;
    }

    public Sonderziele front(String front) {
        this.setFront(front);
        return this;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getSeite1() {
        return this.seite1;
    }

    public Sonderziele seite1(String seite1) {
        this.setSeite1(seite1);
        return this;
    }

    public void setSeite1(String seite1) {
        this.seite1 = seite1;
    }

    public String getSeite2() {
        return this.seite2;
    }

    public Sonderziele seite2(String seite2) {
        this.setSeite2(seite2);
        return this;
    }

    public void setSeite2(String seite2) {
        this.seite2 = seite2;
    }

    public String getInnen() {
        return this.innen;
    }

    public Sonderziele innen(String innen) {
        this.setInnen(innen);
        return this;
    }

    public void setInnen(String innen) {
        this.innen = innen;
    }

    public String getTft() {
        return this.tft;
    }

    public Sonderziele tft(String tft) {
        this.setTft(tft);
        return this;
    }

    public void setTft(String tft) {
        this.tft = tft;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public Sonderziele terminal(String terminal) {
        this.setTerminal(terminal);
        return this;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sonderziele)) {
            return false;
        }
        return id != null && id.equals(((Sonderziele) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sonderziele{" +
            "id=" + getId() +
            ", zugnummer='" + getZugnummer() + "'" +
            ", front='" + getFront() + "'" +
            ", seite1='" + getSeite1() + "'" +
            ", seite2='" + getSeite2() + "'" +
            ", innen='" + getInnen() + "'" +
            ", tft='" + getTft() + "'" +
            ", terminal='" + getTerminal() + "'" +
            "}";
    }
}
