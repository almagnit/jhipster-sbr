package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ort.
 */
@Entity
@Table(name = "ort")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ibnr")
    private String ibnr;

    @Column(name = "ds_100")
    private String ds100;

    @Column(name = "front")
    private String front;

    @Column(name = "seite")
    private String seite;

    @Column(name = "innen")
    private String innen;

    @Column(name = "tft")
    private String tft;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "ds_001_c")
    private String ds001c;

    @Column(name = "video")
    private String video;

    @Column(name = "ds_009")
    private String ds009;

    @Column(name = "ds_003")
    private String ds003;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIbnr() {
        return this.ibnr;
    }

    public Ort ibnr(String ibnr) {
        this.setIbnr(ibnr);
        return this;
    }

    public void setIbnr(String ibnr) {
        this.ibnr = ibnr;
    }

    public String getDs100() {
        return this.ds100;
    }

    public Ort ds100(String ds100) {
        this.setDs100(ds100);
        return this;
    }

    public void setDs100(String ds100) {
        this.ds100 = ds100;
    }

    public String getFront() {
        return this.front;
    }

    public Ort front(String front) {
        this.setFront(front);
        return this;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getSeite() {
        return this.seite;
    }

    public Ort seite(String seite) {
        this.setSeite(seite);
        return this;
    }

    public void setSeite(String seite) {
        this.seite = seite;
    }

    public String getInnen() {
        return this.innen;
    }

    public Ort innen(String innen) {
        this.setInnen(innen);
        return this;
    }

    public void setInnen(String innen) {
        this.innen = innen;
    }

    public String getTft() {
        return this.tft;
    }

    public Ort tft(String tft) {
        this.setTft(tft);
        return this;
    }

    public void setTft(String tft) {
        this.tft = tft;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public Ort terminal(String terminal) {
        this.setTerminal(terminal);
        return this;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDs001c() {
        return this.ds001c;
    }

    public Ort ds001c(String ds001c) {
        this.setDs001c(ds001c);
        return this;
    }

    public void setDs001c(String ds001c) {
        this.ds001c = ds001c;
    }

    public String getVideo() {
        return this.video;
    }

    public Ort video(String video) {
        this.setVideo(video);
        return this;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDs009() {
        return this.ds009;
    }

    public Ort ds009(String ds009) {
        this.setDs009(ds009);
        return this;
    }

    public void setDs009(String ds009) {
        this.ds009 = ds009;
    }

    public String getDs003() {
        return this.ds003;
    }

    public Ort ds003(String ds003) {
        this.setDs003(ds003);
        return this;
    }

    public void setDs003(String ds003) {
        this.ds003 = ds003;
    }

    public String getLanguage() {
        return this.language;
    }

    public Ort language(String language) {
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
        if (!(o instanceof Ort)) {
            return false;
        }
        return id != null && id.equals(((Ort) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ort{" +
            "id=" + getId() +
            ", ibnr='" + getIbnr() + "'" +
            ", ds100='" + getDs100() + "'" +
            ", front='" + getFront() + "'" +
            ", seite='" + getSeite() + "'" +
            ", innen='" + getInnen() + "'" +
            ", tft='" + getTft() + "'" +
            ", terminal='" + getTerminal() + "'" +
            ", ds001c='" + getDs001c() + "'" +
            ", video='" + getVideo() + "'" +
            ", ds009='" + getDs009() + "'" +
            ", ds003='" + getDs003() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
