package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SpecialInfo.
 */
@Entity
@Table(name = "special_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SpecialInfo implements Serializable {

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

    @Column(name = "attribute")
    private String attribute;

    @Column(name = "short_terminal_desc")
    private String shortTerminalDesc;

    @Column(name = "long_terminal_desc")
    private String longTerminalDesc;

    @Column(name = "display_text")
    private String displayText;

    @Column(name = "ds_003")
    private String ds003;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SpecialInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public SpecialInfo code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItem() {
        return this.item;
    }

    public SpecialInfo item(String item) {
        this.setItem(item);
        return this;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public SpecialInfo attribute(String attribute) {
        this.setAttribute(attribute);
        return this;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getShortTerminalDesc() {
        return this.shortTerminalDesc;
    }

    public SpecialInfo shortTerminalDesc(String shortTerminalDesc) {
        this.setShortTerminalDesc(shortTerminalDesc);
        return this;
    }

    public void setShortTerminalDesc(String shortTerminalDesc) {
        this.shortTerminalDesc = shortTerminalDesc;
    }

    public String getLongTerminalDesc() {
        return this.longTerminalDesc;
    }

    public SpecialInfo longTerminalDesc(String longTerminalDesc) {
        this.setLongTerminalDesc(longTerminalDesc);
        return this;
    }

    public void setLongTerminalDesc(String longTerminalDesc) {
        this.longTerminalDesc = longTerminalDesc;
    }

    public String getDisplayText() {
        return this.displayText;
    }

    public SpecialInfo displayText(String displayText) {
        this.setDisplayText(displayText);
        return this;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getDs003() {
        return this.ds003;
    }

    public SpecialInfo ds003(String ds003) {
        this.setDs003(ds003);
        return this;
    }

    public void setDs003(String ds003) {
        this.ds003 = ds003;
    }

    public String getLanguage() {
        return this.language;
    }

    public SpecialInfo language(String language) {
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
        if (!(o instanceof SpecialInfo)) {
            return false;
        }
        return id != null && id.equals(((SpecialInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecialInfo{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", item='" + getItem() + "'" +
            ", attribute='" + getAttribute() + "'" +
            ", shortTerminalDesc='" + getShortTerminalDesc() + "'" +
            ", longTerminalDesc='" + getLongTerminalDesc() + "'" +
            ", displayText='" + getDisplayText() + "'" +
            ", ds003='" + getDs003() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
