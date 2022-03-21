package sbr.converter.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Route.
 */
@Entity
@Table(name = "route")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ort")
    private String ort;

    @Column(name = "entfernung")
    private String entfernung;

    @Column(name = "haltegrund")
    private String haltegrund;

    @Column(name = "gleis")
    private String gleis;

    @Column(name = "ausstieg")
    private String ausstieg;

    @Column(name = "gps")
    private String gps;

    @Column(name = "ansage")
    private String ansage;

    @Column(name = "end_ansage")
    private String endAnsage;

    @Column(name = "start_ansage")
    private String startAnsage;

    @Column(name = "via_ansage")
    private String viaAnsage;

    @Column(name = "stop_ansage")
    private String stopAnsage;

    @Column(name = "stop_ansage_mode")
    private String stopAnsageMode;

    @Column(name = "zugziel")
    private String zugziel;

    @Column(name = "entwerter_1")
    private String entwerter1;

    @Column(name = "entwerter_2")
    private String entwerter2;

    @Column(name = "zoneninfo")
    private String zoneninfo;

    @Column(name = "automat_10")
    private String automat10;

    @Column(name = "attribute")
    private String attribute;

    @Column(name = "sprache_1")
    private String sprache1;

    @Column(name = "sprache_2")
    private String sprache2;

    @Column(name = "sprache_3")
    private String sprache3;

    @Column(name = "kurs")
    private String kurs;

    @Column(name = "zieltexte")
    private String zieltexte;

    @Column(name = "liniennummer")
    private String liniennummer;

    @Column(name = "gattung")
    private String gattung;

    @Column(name = "line_marker")
    private String lineMarker;

    @Column(name = "ds_010")
    private String ds010;

    @Column(name = "linienansage")
    private String linienansage;

    @Column(name = "spurkranz")
    private String spurkranz;

    @Column(name = "sk_dauer")
    private String skDauer;

    @Column(name = "sk_boogie")
    private String skBoogie;

    @Column(name = "prm_tur")
    private String prmTur;

    @Column(name = "tursperrung")
    private String tursperrung;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Route id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrt() {
        return this.ort;
    }

    public Route ort(String ort) {
        this.setOrt(ort);
        return this;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getEntfernung() {
        return this.entfernung;
    }

    public Route entfernung(String entfernung) {
        this.setEntfernung(entfernung);
        return this;
    }

    public void setEntfernung(String entfernung) {
        this.entfernung = entfernung;
    }

    public String getHaltegrund() {
        return this.haltegrund;
    }

    public Route haltegrund(String haltegrund) {
        this.setHaltegrund(haltegrund);
        return this;
    }

    public void setHaltegrund(String haltegrund) {
        this.haltegrund = haltegrund;
    }

    public String getGleis() {
        return this.gleis;
    }

    public Route gleis(String gleis) {
        this.setGleis(gleis);
        return this;
    }

    public void setGleis(String gleis) {
        this.gleis = gleis;
    }

    public String getAusstieg() {
        return this.ausstieg;
    }

    public Route ausstieg(String ausstieg) {
        this.setAusstieg(ausstieg);
        return this;
    }

    public void setAusstieg(String ausstieg) {
        this.ausstieg = ausstieg;
    }

    public String getGps() {
        return this.gps;
    }

    public Route gps(String gps) {
        this.setGps(gps);
        return this;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getAnsage() {
        return this.ansage;
    }

    public Route ansage(String ansage) {
        this.setAnsage(ansage);
        return this;
    }

    public void setAnsage(String ansage) {
        this.ansage = ansage;
    }

    public String getEndAnsage() {
        return this.endAnsage;
    }

    public Route endAnsage(String endAnsage) {
        this.setEndAnsage(endAnsage);
        return this;
    }

    public void setEndAnsage(String endAnsage) {
        this.endAnsage = endAnsage;
    }

    public String getStartAnsage() {
        return this.startAnsage;
    }

    public Route startAnsage(String startAnsage) {
        this.setStartAnsage(startAnsage);
        return this;
    }

    public void setStartAnsage(String startAnsage) {
        this.startAnsage = startAnsage;
    }

    public String getViaAnsage() {
        return this.viaAnsage;
    }

    public Route viaAnsage(String viaAnsage) {
        this.setViaAnsage(viaAnsage);
        return this;
    }

    public void setViaAnsage(String viaAnsage) {
        this.viaAnsage = viaAnsage;
    }

    public String getStopAnsage() {
        return this.stopAnsage;
    }

    public Route stopAnsage(String stopAnsage) {
        this.setStopAnsage(stopAnsage);
        return this;
    }

    public void setStopAnsage(String stopAnsage) {
        this.stopAnsage = stopAnsage;
    }

    public String getStopAnsageMode() {
        return this.stopAnsageMode;
    }

    public Route stopAnsageMode(String stopAnsageMode) {
        this.setStopAnsageMode(stopAnsageMode);
        return this;
    }

    public void setStopAnsageMode(String stopAnsageMode) {
        this.stopAnsageMode = stopAnsageMode;
    }

    public String getZugziel() {
        return this.zugziel;
    }

    public Route zugziel(String zugziel) {
        this.setZugziel(zugziel);
        return this;
    }

    public void setZugziel(String zugziel) {
        this.zugziel = zugziel;
    }

    public String getEntwerter1() {
        return this.entwerter1;
    }

    public Route entwerter1(String entwerter1) {
        this.setEntwerter1(entwerter1);
        return this;
    }

    public void setEntwerter1(String entwerter1) {
        this.entwerter1 = entwerter1;
    }

    public String getEntwerter2() {
        return this.entwerter2;
    }

    public Route entwerter2(String entwerter2) {
        this.setEntwerter2(entwerter2);
        return this;
    }

    public void setEntwerter2(String entwerter2) {
        this.entwerter2 = entwerter2;
    }

    public String getZoneninfo() {
        return this.zoneninfo;
    }

    public Route zoneninfo(String zoneninfo) {
        this.setZoneninfo(zoneninfo);
        return this;
    }

    public void setZoneninfo(String zoneninfo) {
        this.zoneninfo = zoneninfo;
    }

    public String getAutomat10() {
        return this.automat10;
    }

    public Route automat10(String automat10) {
        this.setAutomat10(automat10);
        return this;
    }

    public void setAutomat10(String automat10) {
        this.automat10 = automat10;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public Route attribute(String attribute) {
        this.setAttribute(attribute);
        return this;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSprache1() {
        return this.sprache1;
    }

    public Route sprache1(String sprache1) {
        this.setSprache1(sprache1);
        return this;
    }

    public void setSprache1(String sprache1) {
        this.sprache1 = sprache1;
    }

    public String getSprache2() {
        return this.sprache2;
    }

    public Route sprache2(String sprache2) {
        this.setSprache2(sprache2);
        return this;
    }

    public void setSprache2(String sprache2) {
        this.sprache2 = sprache2;
    }

    public String getSprache3() {
        return this.sprache3;
    }

    public Route sprache3(String sprache3) {
        this.setSprache3(sprache3);
        return this;
    }

    public void setSprache3(String sprache3) {
        this.sprache3 = sprache3;
    }

    public String getKurs() {
        return this.kurs;
    }

    public Route kurs(String kurs) {
        this.setKurs(kurs);
        return this;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getZieltexte() {
        return this.zieltexte;
    }

    public Route zieltexte(String zieltexte) {
        this.setZieltexte(zieltexte);
        return this;
    }

    public void setZieltexte(String zieltexte) {
        this.zieltexte = zieltexte;
    }

    public String getLiniennummer() {
        return this.liniennummer;
    }

    public Route liniennummer(String liniennummer) {
        this.setLiniennummer(liniennummer);
        return this;
    }

    public void setLiniennummer(String liniennummer) {
        this.liniennummer = liniennummer;
    }

    public String getGattung() {
        return this.gattung;
    }

    public Route gattung(String gattung) {
        this.setGattung(gattung);
        return this;
    }

    public void setGattung(String gattung) {
        this.gattung = gattung;
    }

    public String getLineMarker() {
        return this.lineMarker;
    }

    public Route lineMarker(String lineMarker) {
        this.setLineMarker(lineMarker);
        return this;
    }

    public void setLineMarker(String lineMarker) {
        this.lineMarker = lineMarker;
    }

    public String getDs010() {
        return this.ds010;
    }

    public Route ds010(String ds010) {
        this.setDs010(ds010);
        return this;
    }

    public void setDs010(String ds010) {
        this.ds010 = ds010;
    }

    public String getLinienansage() {
        return this.linienansage;
    }

    public Route linienansage(String linienansage) {
        this.setLinienansage(linienansage);
        return this;
    }

    public void setLinienansage(String linienansage) {
        this.linienansage = linienansage;
    }

    public String getSpurkranz() {
        return this.spurkranz;
    }

    public Route spurkranz(String spurkranz) {
        this.setSpurkranz(spurkranz);
        return this;
    }

    public void setSpurkranz(String spurkranz) {
        this.spurkranz = spurkranz;
    }

    public String getSkDauer() {
        return this.skDauer;
    }

    public Route skDauer(String skDauer) {
        this.setSkDauer(skDauer);
        return this;
    }

    public void setSkDauer(String skDauer) {
        this.skDauer = skDauer;
    }

    public String getSkBoogie() {
        return this.skBoogie;
    }

    public Route skBoogie(String skBoogie) {
        this.setSkBoogie(skBoogie);
        return this;
    }

    public void setSkBoogie(String skBoogie) {
        this.skBoogie = skBoogie;
    }

    public String getPrmTur() {
        return this.prmTur;
    }

    public Route prmTur(String prmTur) {
        this.setPrmTur(prmTur);
        return this;
    }

    public void setPrmTur(String prmTur) {
        this.prmTur = prmTur;
    }

    public String getTursperrung() {
        return this.tursperrung;
    }

    public Route tursperrung(String tursperrung) {
        this.setTursperrung(tursperrung);
        return this;
    }

    public void setTursperrung(String tursperrung) {
        this.tursperrung = tursperrung;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Route)) {
            return false;
        }
        return id != null && id.equals(((Route) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Route{" +
            "id=" + getId() +
            ", ort='" + getOrt() + "'" +
            ", entfernung='" + getEntfernung() + "'" +
            ", haltegrund='" + getHaltegrund() + "'" +
            ", gleis='" + getGleis() + "'" +
            ", ausstieg='" + getAusstieg() + "'" +
            ", gps='" + getGps() + "'" +
            ", ansage='" + getAnsage() + "'" +
            ", endAnsage='" + getEndAnsage() + "'" +
            ", startAnsage='" + getStartAnsage() + "'" +
            ", viaAnsage='" + getViaAnsage() + "'" +
            ", stopAnsage='" + getStopAnsage() + "'" +
            ", stopAnsageMode='" + getStopAnsageMode() + "'" +
            ", zugziel='" + getZugziel() + "'" +
            ", entwerter1='" + getEntwerter1() + "'" +
            ", entwerter2='" + getEntwerter2() + "'" +
            ", zoneninfo='" + getZoneninfo() + "'" +
            ", automat10='" + getAutomat10() + "'" +
            ", attribute='" + getAttribute() + "'" +
            ", sprache1='" + getSprache1() + "'" +
            ", sprache2='" + getSprache2() + "'" +
            ", sprache3='" + getSprache3() + "'" +
            ", kurs='" + getKurs() + "'" +
            ", zieltexte='" + getZieltexte() + "'" +
            ", liniennummer='" + getLiniennummer() + "'" +
            ", gattung='" + getGattung() + "'" +
            ", lineMarker='" + getLineMarker() + "'" +
            ", ds010='" + getDs010() + "'" +
            ", linienansage='" + getLinienansage() + "'" +
            ", spurkranz='" + getSpurkranz() + "'" +
            ", skDauer='" + getSkDauer() + "'" +
            ", skBoogie='" + getSkBoogie() + "'" +
            ", prmTur='" + getPrmTur() + "'" +
            ", tursperrung='" + getTursperrung() + "'" +
            "}";
    }
}
