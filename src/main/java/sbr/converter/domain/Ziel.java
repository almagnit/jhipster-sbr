package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ziel.
 */
@Entity
@Table(name = "ziel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ziel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

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

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ziel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Ziel code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFront() {
        return this.front;
    }

    public Ziel front(String front) {
        this.setFront(front);
        return this;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getSeite1() {
        return this.seite1;
    }

    public Ziel seite1(String seite1) {
        this.setSeite1(seite1);
        return this;
    }

    public void setSeite1(String seite1) {
        this.seite1 = seite1;
    }

    public String getSeite2() {
        return this.seite2;
    }

    public Ziel seite2(String seite2) {
        this.setSeite2(seite2);
        return this;
    }

    public void setSeite2(String seite2) {
        this.seite2 = seite2;
    }

    public String getInnen() {
        return this.innen;
    }

    public Ziel innen(String innen) {
        this.setInnen(innen);
        return this;
    }

    public void setInnen(String innen) {
        this.innen = innen;
    }

    public String getTft() {
        return this.tft;
    }

    public Ziel tft(String tft) {
        this.setTft(tft);
        return this;
    }

    public void setTft(String tft) {
        this.tft = tft;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public Ziel terminal(String terminal) {
        this.setTerminal(terminal);
        return this;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getLanguage() {
        return this.language;
    }

    public Ziel language(String language) {
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
        if (!(o instanceof Ziel)) {
            return false;
        }
        return id != null && id.equals(((Ziel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ziel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", front='" + getFront() + "'" +
            ", seite1='" + getSeite1() + "'" +
            ", seite2='" + getSeite2() + "'" +
            ", innen='" + getInnen() + "'" +
            ", tft='" + getTft() + "'" +
            ", terminal='" + getTerminal() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
