package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fahrten.
 */
@Entity
@Table(name = "fahrten")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fahrten implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "titel")
    private String titel;

    @Column(name = "zugnummer")
    private String zugnummer;

    @Column(name = "znr_beschreibung")
    private String znrBeschreibung;

    @Column(name = "variante")
    private String variante;

    @Column(name = "tagesart")
    private String tagesart;

    @Column(name = "umlauf")
    private String umlauf;

    @Column(name = "umlaufindex")
    private String umlaufindex;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fahrten id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return this.titel;
    }

    public Fahrten titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getZugnummer() {
        return this.zugnummer;
    }

    public Fahrten zugnummer(String zugnummer) {
        this.setZugnummer(zugnummer);
        return this;
    }

    public void setZugnummer(String zugnummer) {
        this.zugnummer = zugnummer;
    }

    public String getZnrBeschreibung() {
        return this.znrBeschreibung;
    }

    public Fahrten znrBeschreibung(String znrBeschreibung) {
        this.setZnrBeschreibung(znrBeschreibung);
        return this;
    }

    public void setZnrBeschreibung(String znrBeschreibung) {
        this.znrBeschreibung = znrBeschreibung;
    }

    public String getVariante() {
        return this.variante;
    }

    public Fahrten variante(String variante) {
        this.setVariante(variante);
        return this;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public String getTagesart() {
        return this.tagesart;
    }

    public Fahrten tagesart(String tagesart) {
        this.setTagesart(tagesart);
        return this;
    }

    public void setTagesart(String tagesart) {
        this.tagesart = tagesart;
    }

    public String getUmlauf() {
        return this.umlauf;
    }

    public Fahrten umlauf(String umlauf) {
        this.setUmlauf(umlauf);
        return this;
    }

    public void setUmlauf(String umlauf) {
        this.umlauf = umlauf;
    }

    public String getUmlaufindex() {
        return this.umlaufindex;
    }

    public Fahrten umlaufindex(String umlaufindex) {
        this.setUmlaufindex(umlaufindex);
        return this;
    }

    public void setUmlaufindex(String umlaufindex) {
        this.umlaufindex = umlaufindex;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fahrten)) {
            return false;
        }
        return id != null && id.equals(((Fahrten) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fahrten{" +
            "id=" + getId() +
            ", titel='" + getTitel() + "'" +
            ", zugnummer='" + getZugnummer() + "'" +
            ", znrBeschreibung='" + getZnrBeschreibung() + "'" +
            ", variante='" + getVariante() + "'" +
            ", tagesart='" + getTagesart() + "'" +
            ", umlauf='" + getUmlauf() + "'" +
            ", umlaufindex='" + getUmlaufindex() + "'" +
            "}";
    }
}
