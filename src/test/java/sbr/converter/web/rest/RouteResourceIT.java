package sbr.converter.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sbr.converter.IntegrationTest;
import sbr.converter.domain.Route;
import sbr.converter.repository.RouteRepository;

/**
 * Integration tests for the {@link RouteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RouteResourceIT {

    private static final String DEFAULT_ORT = "AAAAAAAAAA";
    private static final String UPDATED_ORT = "BBBBBBBBBB";

    private static final String DEFAULT_ENTFERNUNG = "AAAAAAAAAA";
    private static final String UPDATED_ENTFERNUNG = "BBBBBBBBBB";

    private static final String DEFAULT_HALTEGRUND = "AAAAAAAAAA";
    private static final String UPDATED_HALTEGRUND = "BBBBBBBBBB";

    private static final String DEFAULT_GLEIS = "AAAAAAAAAA";
    private static final String UPDATED_GLEIS = "BBBBBBBBBB";

    private static final String DEFAULT_AUSSTIEG = "AAAAAAAAAA";
    private static final String UPDATED_AUSSTIEG = "BBBBBBBBBB";

    private static final String DEFAULT_GPS = "AAAAAAAAAA";
    private static final String UPDATED_GPS = "BBBBBBBBBB";

    private static final String DEFAULT_ANSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ANSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_END_ANSAGE = "AAAAAAAAAA";
    private static final String UPDATED_END_ANSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_START_ANSAGE = "AAAAAAAAAA";
    private static final String UPDATED_START_ANSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_VIA_ANSAGE = "AAAAAAAAAA";
    private static final String UPDATED_VIA_ANSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_STOP_ANSAGE = "AAAAAAAAAA";
    private static final String UPDATED_STOP_ANSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_STOP_ANSAGE_MODE = "AAAAAAAAAA";
    private static final String UPDATED_STOP_ANSAGE_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_ZUGZIEL = "AAAAAAAAAA";
    private static final String UPDATED_ZUGZIEL = "BBBBBBBBBB";

    private static final String DEFAULT_ENTWERTER_1 = "AAAAAAAAAA";
    private static final String UPDATED_ENTWERTER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ENTWERTER_2 = "AAAAAAAAAA";
    private static final String UPDATED_ENTWERTER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ZONENINFO = "AAAAAAAAAA";
    private static final String UPDATED_ZONENINFO = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOMAT_10 = "AAAAAAAAAA";
    private static final String UPDATED_AUTOMAT_10 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE = "BBBBBBBBBB";

    private static final String DEFAULT_SPRACHE_1 = "AAAAAAAAAA";
    private static final String UPDATED_SPRACHE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SPRACHE_2 = "AAAAAAAAAA";
    private static final String UPDATED_SPRACHE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SPRACHE_3 = "AAAAAAAAAA";
    private static final String UPDATED_SPRACHE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_KURS = "AAAAAAAAAA";
    private static final String UPDATED_KURS = "BBBBBBBBBB";

    private static final String DEFAULT_ZIELTEXTE = "AAAAAAAAAA";
    private static final String UPDATED_ZIELTEXTE = "BBBBBBBBBB";

    private static final String DEFAULT_LINIENNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_LINIENNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_GATTUNG = "AAAAAAAAAA";
    private static final String UPDATED_GATTUNG = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_MARKER = "AAAAAAAAAA";
    private static final String UPDATED_LINE_MARKER = "BBBBBBBBBB";

    private static final String DEFAULT_DS_010 = "AAAAAAAAAA";
    private static final String UPDATED_DS_010 = "BBBBBBBBBB";

    private static final String DEFAULT_LINIENANSAGE = "AAAAAAAAAA";
    private static final String UPDATED_LINIENANSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_SPURKRANZ = "AAAAAAAAAA";
    private static final String UPDATED_SPURKRANZ = "BBBBBBBBBB";

    private static final String DEFAULT_SK_DAUER = "AAAAAAAAAA";
    private static final String UPDATED_SK_DAUER = "BBBBBBBBBB";

    private static final String DEFAULT_SK_BOOGIE = "AAAAAAAAAA";
    private static final String UPDATED_SK_BOOGIE = "BBBBBBBBBB";

    private static final String DEFAULT_PRM_TUR = "AAAAAAAAAA";
    private static final String UPDATED_PRM_TUR = "BBBBBBBBBB";

    private static final String DEFAULT_TURSPERRUNG = "AAAAAAAAAA";
    private static final String UPDATED_TURSPERRUNG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/routes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRouteMockMvc;

    private Route route;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Route createEntity(EntityManager em) {
        Route route = new Route()
            .ort(DEFAULT_ORT)
            .entfernung(DEFAULT_ENTFERNUNG)
            .haltegrund(DEFAULT_HALTEGRUND)
            .gleis(DEFAULT_GLEIS)
            .ausstieg(DEFAULT_AUSSTIEG)
            .gps(DEFAULT_GPS)
            .ansage(DEFAULT_ANSAGE)
            .endAnsage(DEFAULT_END_ANSAGE)
            .startAnsage(DEFAULT_START_ANSAGE)
            .viaAnsage(DEFAULT_VIA_ANSAGE)
            .stopAnsage(DEFAULT_STOP_ANSAGE)
            .stopAnsageMode(DEFAULT_STOP_ANSAGE_MODE)
            .zugziel(DEFAULT_ZUGZIEL)
            .entwerter1(DEFAULT_ENTWERTER_1)
            .entwerter2(DEFAULT_ENTWERTER_2)
            .zoneninfo(DEFAULT_ZONENINFO)
            .automat10(DEFAULT_AUTOMAT_10)
            .attribute(DEFAULT_ATTRIBUTE)
            .sprache1(DEFAULT_SPRACHE_1)
            .sprache2(DEFAULT_SPRACHE_2)
            .sprache3(DEFAULT_SPRACHE_3)
            .kurs(DEFAULT_KURS)
            .zieltexte(DEFAULT_ZIELTEXTE)
            .liniennummer(DEFAULT_LINIENNUMMER)
            .gattung(DEFAULT_GATTUNG)
            .lineMarker(DEFAULT_LINE_MARKER)
            .ds010(DEFAULT_DS_010)
            .linienansage(DEFAULT_LINIENANSAGE)
            .spurkranz(DEFAULT_SPURKRANZ)
            .skDauer(DEFAULT_SK_DAUER)
            .skBoogie(DEFAULT_SK_BOOGIE)
            .prmTur(DEFAULT_PRM_TUR)
            .tursperrung(DEFAULT_TURSPERRUNG);
        return route;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Route createUpdatedEntity(EntityManager em) {
        Route route = new Route()
            .ort(UPDATED_ORT)
            .entfernung(UPDATED_ENTFERNUNG)
            .haltegrund(UPDATED_HALTEGRUND)
            .gleis(UPDATED_GLEIS)
            .ausstieg(UPDATED_AUSSTIEG)
            .gps(UPDATED_GPS)
            .ansage(UPDATED_ANSAGE)
            .endAnsage(UPDATED_END_ANSAGE)
            .startAnsage(UPDATED_START_ANSAGE)
            .viaAnsage(UPDATED_VIA_ANSAGE)
            .stopAnsage(UPDATED_STOP_ANSAGE)
            .stopAnsageMode(UPDATED_STOP_ANSAGE_MODE)
            .zugziel(UPDATED_ZUGZIEL)
            .entwerter1(UPDATED_ENTWERTER_1)
            .entwerter2(UPDATED_ENTWERTER_2)
            .zoneninfo(UPDATED_ZONENINFO)
            .automat10(UPDATED_AUTOMAT_10)
            .attribute(UPDATED_ATTRIBUTE)
            .sprache1(UPDATED_SPRACHE_1)
            .sprache2(UPDATED_SPRACHE_2)
            .sprache3(UPDATED_SPRACHE_3)
            .kurs(UPDATED_KURS)
            .zieltexte(UPDATED_ZIELTEXTE)
            .liniennummer(UPDATED_LINIENNUMMER)
            .gattung(UPDATED_GATTUNG)
            .lineMarker(UPDATED_LINE_MARKER)
            .ds010(UPDATED_DS_010)
            .linienansage(UPDATED_LINIENANSAGE)
            .spurkranz(UPDATED_SPURKRANZ)
            .skDauer(UPDATED_SK_DAUER)
            .skBoogie(UPDATED_SK_BOOGIE)
            .prmTur(UPDATED_PRM_TUR)
            .tursperrung(UPDATED_TURSPERRUNG);
        return route;
    }

    @BeforeEach
    public void initTest() {
        route = createEntity(em);
    }

    @Test
    @Transactional
    void createRoute() throws Exception {
        int databaseSizeBeforeCreate = routeRepository.findAll().size();
        // Create the Route
        restRouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(route)))
            .andExpect(status().isCreated());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeCreate + 1);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getOrt()).isEqualTo(DEFAULT_ORT);
        assertThat(testRoute.getEntfernung()).isEqualTo(DEFAULT_ENTFERNUNG);
        assertThat(testRoute.getHaltegrund()).isEqualTo(DEFAULT_HALTEGRUND);
        assertThat(testRoute.getGleis()).isEqualTo(DEFAULT_GLEIS);
        assertThat(testRoute.getAusstieg()).isEqualTo(DEFAULT_AUSSTIEG);
        assertThat(testRoute.getGps()).isEqualTo(DEFAULT_GPS);
        assertThat(testRoute.getAnsage()).isEqualTo(DEFAULT_ANSAGE);
        assertThat(testRoute.getEndAnsage()).isEqualTo(DEFAULT_END_ANSAGE);
        assertThat(testRoute.getStartAnsage()).isEqualTo(DEFAULT_START_ANSAGE);
        assertThat(testRoute.getViaAnsage()).isEqualTo(DEFAULT_VIA_ANSAGE);
        assertThat(testRoute.getStopAnsage()).isEqualTo(DEFAULT_STOP_ANSAGE);
        assertThat(testRoute.getStopAnsageMode()).isEqualTo(DEFAULT_STOP_ANSAGE_MODE);
        assertThat(testRoute.getZugziel()).isEqualTo(DEFAULT_ZUGZIEL);
        assertThat(testRoute.getEntwerter1()).isEqualTo(DEFAULT_ENTWERTER_1);
        assertThat(testRoute.getEntwerter2()).isEqualTo(DEFAULT_ENTWERTER_2);
        assertThat(testRoute.getZoneninfo()).isEqualTo(DEFAULT_ZONENINFO);
        assertThat(testRoute.getAutomat10()).isEqualTo(DEFAULT_AUTOMAT_10);
        assertThat(testRoute.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
        assertThat(testRoute.getSprache1()).isEqualTo(DEFAULT_SPRACHE_1);
        assertThat(testRoute.getSprache2()).isEqualTo(DEFAULT_SPRACHE_2);
        assertThat(testRoute.getSprache3()).isEqualTo(DEFAULT_SPRACHE_3);
        assertThat(testRoute.getKurs()).isEqualTo(DEFAULT_KURS);
        assertThat(testRoute.getZieltexte()).isEqualTo(DEFAULT_ZIELTEXTE);
        assertThat(testRoute.getLiniennummer()).isEqualTo(DEFAULT_LINIENNUMMER);
        assertThat(testRoute.getGattung()).isEqualTo(DEFAULT_GATTUNG);
        assertThat(testRoute.getLineMarker()).isEqualTo(DEFAULT_LINE_MARKER);
        assertThat(testRoute.getDs010()).isEqualTo(DEFAULT_DS_010);
        assertThat(testRoute.getLinienansage()).isEqualTo(DEFAULT_LINIENANSAGE);
        assertThat(testRoute.getSpurkranz()).isEqualTo(DEFAULT_SPURKRANZ);
        assertThat(testRoute.getSkDauer()).isEqualTo(DEFAULT_SK_DAUER);
        assertThat(testRoute.getSkBoogie()).isEqualTo(DEFAULT_SK_BOOGIE);
        assertThat(testRoute.getPrmTur()).isEqualTo(DEFAULT_PRM_TUR);
        assertThat(testRoute.getTursperrung()).isEqualTo(DEFAULT_TURSPERRUNG);
    }

    @Test
    @Transactional
    void createRouteWithExistingId() throws Exception {
        // Create the Route with an existing ID
        route.setId(1L);

        int databaseSizeBeforeCreate = routeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(route)))
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoutes() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get all the routeList
        restRouteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(route.getId().intValue())))
            .andExpect(jsonPath("$.[*].ort").value(hasItem(DEFAULT_ORT)))
            .andExpect(jsonPath("$.[*].entfernung").value(hasItem(DEFAULT_ENTFERNUNG)))
            .andExpect(jsonPath("$.[*].haltegrund").value(hasItem(DEFAULT_HALTEGRUND)))
            .andExpect(jsonPath("$.[*].gleis").value(hasItem(DEFAULT_GLEIS)))
            .andExpect(jsonPath("$.[*].ausstieg").value(hasItem(DEFAULT_AUSSTIEG)))
            .andExpect(jsonPath("$.[*].gps").value(hasItem(DEFAULT_GPS)))
            .andExpect(jsonPath("$.[*].ansage").value(hasItem(DEFAULT_ANSAGE)))
            .andExpect(jsonPath("$.[*].endAnsage").value(hasItem(DEFAULT_END_ANSAGE)))
            .andExpect(jsonPath("$.[*].startAnsage").value(hasItem(DEFAULT_START_ANSAGE)))
            .andExpect(jsonPath("$.[*].viaAnsage").value(hasItem(DEFAULT_VIA_ANSAGE)))
            .andExpect(jsonPath("$.[*].stopAnsage").value(hasItem(DEFAULT_STOP_ANSAGE)))
            .andExpect(jsonPath("$.[*].stopAnsageMode").value(hasItem(DEFAULT_STOP_ANSAGE_MODE)))
            .andExpect(jsonPath("$.[*].zugziel").value(hasItem(DEFAULT_ZUGZIEL)))
            .andExpect(jsonPath("$.[*].entwerter1").value(hasItem(DEFAULT_ENTWERTER_1)))
            .andExpect(jsonPath("$.[*].entwerter2").value(hasItem(DEFAULT_ENTWERTER_2)))
            .andExpect(jsonPath("$.[*].zoneninfo").value(hasItem(DEFAULT_ZONENINFO)))
            .andExpect(jsonPath("$.[*].automat10").value(hasItem(DEFAULT_AUTOMAT_10)))
            .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE)))
            .andExpect(jsonPath("$.[*].sprache1").value(hasItem(DEFAULT_SPRACHE_1)))
            .andExpect(jsonPath("$.[*].sprache2").value(hasItem(DEFAULT_SPRACHE_2)))
            .andExpect(jsonPath("$.[*].sprache3").value(hasItem(DEFAULT_SPRACHE_3)))
            .andExpect(jsonPath("$.[*].kurs").value(hasItem(DEFAULT_KURS)))
            .andExpect(jsonPath("$.[*].zieltexte").value(hasItem(DEFAULT_ZIELTEXTE)))
            .andExpect(jsonPath("$.[*].liniennummer").value(hasItem(DEFAULT_LINIENNUMMER)))
            .andExpect(jsonPath("$.[*].gattung").value(hasItem(DEFAULT_GATTUNG)))
            .andExpect(jsonPath("$.[*].lineMarker").value(hasItem(DEFAULT_LINE_MARKER)))
            .andExpect(jsonPath("$.[*].ds010").value(hasItem(DEFAULT_DS_010)))
            .andExpect(jsonPath("$.[*].linienansage").value(hasItem(DEFAULT_LINIENANSAGE)))
            .andExpect(jsonPath("$.[*].spurkranz").value(hasItem(DEFAULT_SPURKRANZ)))
            .andExpect(jsonPath("$.[*].skDauer").value(hasItem(DEFAULT_SK_DAUER)))
            .andExpect(jsonPath("$.[*].skBoogie").value(hasItem(DEFAULT_SK_BOOGIE)))
            .andExpect(jsonPath("$.[*].prmTur").value(hasItem(DEFAULT_PRM_TUR)))
            .andExpect(jsonPath("$.[*].tursperrung").value(hasItem(DEFAULT_TURSPERRUNG)));
    }

    @Test
    @Transactional
    void getRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get the route
        restRouteMockMvc
            .perform(get(ENTITY_API_URL_ID, route.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(route.getId().intValue()))
            .andExpect(jsonPath("$.ort").value(DEFAULT_ORT))
            .andExpect(jsonPath("$.entfernung").value(DEFAULT_ENTFERNUNG))
            .andExpect(jsonPath("$.haltegrund").value(DEFAULT_HALTEGRUND))
            .andExpect(jsonPath("$.gleis").value(DEFAULT_GLEIS))
            .andExpect(jsonPath("$.ausstieg").value(DEFAULT_AUSSTIEG))
            .andExpect(jsonPath("$.gps").value(DEFAULT_GPS))
            .andExpect(jsonPath("$.ansage").value(DEFAULT_ANSAGE))
            .andExpect(jsonPath("$.endAnsage").value(DEFAULT_END_ANSAGE))
            .andExpect(jsonPath("$.startAnsage").value(DEFAULT_START_ANSAGE))
            .andExpect(jsonPath("$.viaAnsage").value(DEFAULT_VIA_ANSAGE))
            .andExpect(jsonPath("$.stopAnsage").value(DEFAULT_STOP_ANSAGE))
            .andExpect(jsonPath("$.stopAnsageMode").value(DEFAULT_STOP_ANSAGE_MODE))
            .andExpect(jsonPath("$.zugziel").value(DEFAULT_ZUGZIEL))
            .andExpect(jsonPath("$.entwerter1").value(DEFAULT_ENTWERTER_1))
            .andExpect(jsonPath("$.entwerter2").value(DEFAULT_ENTWERTER_2))
            .andExpect(jsonPath("$.zoneninfo").value(DEFAULT_ZONENINFO))
            .andExpect(jsonPath("$.automat10").value(DEFAULT_AUTOMAT_10))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE))
            .andExpect(jsonPath("$.sprache1").value(DEFAULT_SPRACHE_1))
            .andExpect(jsonPath("$.sprache2").value(DEFAULT_SPRACHE_2))
            .andExpect(jsonPath("$.sprache3").value(DEFAULT_SPRACHE_3))
            .andExpect(jsonPath("$.kurs").value(DEFAULT_KURS))
            .andExpect(jsonPath("$.zieltexte").value(DEFAULT_ZIELTEXTE))
            .andExpect(jsonPath("$.liniennummer").value(DEFAULT_LINIENNUMMER))
            .andExpect(jsonPath("$.gattung").value(DEFAULT_GATTUNG))
            .andExpect(jsonPath("$.lineMarker").value(DEFAULT_LINE_MARKER))
            .andExpect(jsonPath("$.ds010").value(DEFAULT_DS_010))
            .andExpect(jsonPath("$.linienansage").value(DEFAULT_LINIENANSAGE))
            .andExpect(jsonPath("$.spurkranz").value(DEFAULT_SPURKRANZ))
            .andExpect(jsonPath("$.skDauer").value(DEFAULT_SK_DAUER))
            .andExpect(jsonPath("$.skBoogie").value(DEFAULT_SK_BOOGIE))
            .andExpect(jsonPath("$.prmTur").value(DEFAULT_PRM_TUR))
            .andExpect(jsonPath("$.tursperrung").value(DEFAULT_TURSPERRUNG));
    }

    @Test
    @Transactional
    void getNonExistingRoute() throws Exception {
        // Get the route
        restRouteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route
        Route updatedRoute = routeRepository.findById(route.getId()).get();
        // Disconnect from session so that the updates on updatedRoute are not directly saved in db
        em.detach(updatedRoute);
        updatedRoute
            .ort(UPDATED_ORT)
            .entfernung(UPDATED_ENTFERNUNG)
            .haltegrund(UPDATED_HALTEGRUND)
            .gleis(UPDATED_GLEIS)
            .ausstieg(UPDATED_AUSSTIEG)
            .gps(UPDATED_GPS)
            .ansage(UPDATED_ANSAGE)
            .endAnsage(UPDATED_END_ANSAGE)
            .startAnsage(UPDATED_START_ANSAGE)
            .viaAnsage(UPDATED_VIA_ANSAGE)
            .stopAnsage(UPDATED_STOP_ANSAGE)
            .stopAnsageMode(UPDATED_STOP_ANSAGE_MODE)
            .zugziel(UPDATED_ZUGZIEL)
            .entwerter1(UPDATED_ENTWERTER_1)
            .entwerter2(UPDATED_ENTWERTER_2)
            .zoneninfo(UPDATED_ZONENINFO)
            .automat10(UPDATED_AUTOMAT_10)
            .attribute(UPDATED_ATTRIBUTE)
            .sprache1(UPDATED_SPRACHE_1)
            .sprache2(UPDATED_SPRACHE_2)
            .sprache3(UPDATED_SPRACHE_3)
            .kurs(UPDATED_KURS)
            .zieltexte(UPDATED_ZIELTEXTE)
            .liniennummer(UPDATED_LINIENNUMMER)
            .gattung(UPDATED_GATTUNG)
            .lineMarker(UPDATED_LINE_MARKER)
            .ds010(UPDATED_DS_010)
            .linienansage(UPDATED_LINIENANSAGE)
            .spurkranz(UPDATED_SPURKRANZ)
            .skDauer(UPDATED_SK_DAUER)
            .skBoogie(UPDATED_SK_BOOGIE)
            .prmTur(UPDATED_PRM_TUR)
            .tursperrung(UPDATED_TURSPERRUNG);

        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoute))
            )
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testRoute.getEntfernung()).isEqualTo(UPDATED_ENTFERNUNG);
        assertThat(testRoute.getHaltegrund()).isEqualTo(UPDATED_HALTEGRUND);
        assertThat(testRoute.getGleis()).isEqualTo(UPDATED_GLEIS);
        assertThat(testRoute.getAusstieg()).isEqualTo(UPDATED_AUSSTIEG);
        assertThat(testRoute.getGps()).isEqualTo(UPDATED_GPS);
        assertThat(testRoute.getAnsage()).isEqualTo(UPDATED_ANSAGE);
        assertThat(testRoute.getEndAnsage()).isEqualTo(UPDATED_END_ANSAGE);
        assertThat(testRoute.getStartAnsage()).isEqualTo(UPDATED_START_ANSAGE);
        assertThat(testRoute.getViaAnsage()).isEqualTo(UPDATED_VIA_ANSAGE);
        assertThat(testRoute.getStopAnsage()).isEqualTo(UPDATED_STOP_ANSAGE);
        assertThat(testRoute.getStopAnsageMode()).isEqualTo(UPDATED_STOP_ANSAGE_MODE);
        assertThat(testRoute.getZugziel()).isEqualTo(UPDATED_ZUGZIEL);
        assertThat(testRoute.getEntwerter1()).isEqualTo(UPDATED_ENTWERTER_1);
        assertThat(testRoute.getEntwerter2()).isEqualTo(UPDATED_ENTWERTER_2);
        assertThat(testRoute.getZoneninfo()).isEqualTo(UPDATED_ZONENINFO);
        assertThat(testRoute.getAutomat10()).isEqualTo(UPDATED_AUTOMAT_10);
        assertThat(testRoute.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
        assertThat(testRoute.getSprache1()).isEqualTo(UPDATED_SPRACHE_1);
        assertThat(testRoute.getSprache2()).isEqualTo(UPDATED_SPRACHE_2);
        assertThat(testRoute.getSprache3()).isEqualTo(UPDATED_SPRACHE_3);
        assertThat(testRoute.getKurs()).isEqualTo(UPDATED_KURS);
        assertThat(testRoute.getZieltexte()).isEqualTo(UPDATED_ZIELTEXTE);
        assertThat(testRoute.getLiniennummer()).isEqualTo(UPDATED_LINIENNUMMER);
        assertThat(testRoute.getGattung()).isEqualTo(UPDATED_GATTUNG);
        assertThat(testRoute.getLineMarker()).isEqualTo(UPDATED_LINE_MARKER);
        assertThat(testRoute.getDs010()).isEqualTo(UPDATED_DS_010);
        assertThat(testRoute.getLinienansage()).isEqualTo(UPDATED_LINIENANSAGE);
        assertThat(testRoute.getSpurkranz()).isEqualTo(UPDATED_SPURKRANZ);
        assertThat(testRoute.getSkDauer()).isEqualTo(UPDATED_SK_DAUER);
        assertThat(testRoute.getSkBoogie()).isEqualTo(UPDATED_SK_BOOGIE);
        assertThat(testRoute.getPrmTur()).isEqualTo(UPDATED_PRM_TUR);
        assertThat(testRoute.getTursperrung()).isEqualTo(UPDATED_TURSPERRUNG);
    }

    @Test
    @Transactional
    void putNonExistingRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, route.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(route))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(route))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(route)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRouteWithPatch() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route using partial update
        Route partialUpdatedRoute = new Route();
        partialUpdatedRoute.setId(route.getId());

        partialUpdatedRoute
            .entfernung(UPDATED_ENTFERNUNG)
            .gleis(UPDATED_GLEIS)
            .gps(UPDATED_GPS)
            .ansage(UPDATED_ANSAGE)
            .stopAnsage(UPDATED_STOP_ANSAGE)
            .stopAnsageMode(UPDATED_STOP_ANSAGE_MODE)
            .entwerter1(UPDATED_ENTWERTER_1)
            .sprache1(UPDATED_SPRACHE_1)
            .sprache3(UPDATED_SPRACHE_3)
            .liniennummer(UPDATED_LINIENNUMMER)
            .ds010(UPDATED_DS_010)
            .spurkranz(UPDATED_SPURKRANZ)
            .skDauer(UPDATED_SK_DAUER)
            .skBoogie(UPDATED_SK_BOOGIE)
            .tursperrung(UPDATED_TURSPERRUNG);

        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoute))
            )
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getOrt()).isEqualTo(DEFAULT_ORT);
        assertThat(testRoute.getEntfernung()).isEqualTo(UPDATED_ENTFERNUNG);
        assertThat(testRoute.getHaltegrund()).isEqualTo(DEFAULT_HALTEGRUND);
        assertThat(testRoute.getGleis()).isEqualTo(UPDATED_GLEIS);
        assertThat(testRoute.getAusstieg()).isEqualTo(DEFAULT_AUSSTIEG);
        assertThat(testRoute.getGps()).isEqualTo(UPDATED_GPS);
        assertThat(testRoute.getAnsage()).isEqualTo(UPDATED_ANSAGE);
        assertThat(testRoute.getEndAnsage()).isEqualTo(DEFAULT_END_ANSAGE);
        assertThat(testRoute.getStartAnsage()).isEqualTo(DEFAULT_START_ANSAGE);
        assertThat(testRoute.getViaAnsage()).isEqualTo(DEFAULT_VIA_ANSAGE);
        assertThat(testRoute.getStopAnsage()).isEqualTo(UPDATED_STOP_ANSAGE);
        assertThat(testRoute.getStopAnsageMode()).isEqualTo(UPDATED_STOP_ANSAGE_MODE);
        assertThat(testRoute.getZugziel()).isEqualTo(DEFAULT_ZUGZIEL);
        assertThat(testRoute.getEntwerter1()).isEqualTo(UPDATED_ENTWERTER_1);
        assertThat(testRoute.getEntwerter2()).isEqualTo(DEFAULT_ENTWERTER_2);
        assertThat(testRoute.getZoneninfo()).isEqualTo(DEFAULT_ZONENINFO);
        assertThat(testRoute.getAutomat10()).isEqualTo(DEFAULT_AUTOMAT_10);
        assertThat(testRoute.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
        assertThat(testRoute.getSprache1()).isEqualTo(UPDATED_SPRACHE_1);
        assertThat(testRoute.getSprache2()).isEqualTo(DEFAULT_SPRACHE_2);
        assertThat(testRoute.getSprache3()).isEqualTo(UPDATED_SPRACHE_3);
        assertThat(testRoute.getKurs()).isEqualTo(DEFAULT_KURS);
        assertThat(testRoute.getZieltexte()).isEqualTo(DEFAULT_ZIELTEXTE);
        assertThat(testRoute.getLiniennummer()).isEqualTo(UPDATED_LINIENNUMMER);
        assertThat(testRoute.getGattung()).isEqualTo(DEFAULT_GATTUNG);
        assertThat(testRoute.getLineMarker()).isEqualTo(DEFAULT_LINE_MARKER);
        assertThat(testRoute.getDs010()).isEqualTo(UPDATED_DS_010);
        assertThat(testRoute.getLinienansage()).isEqualTo(DEFAULT_LINIENANSAGE);
        assertThat(testRoute.getSpurkranz()).isEqualTo(UPDATED_SPURKRANZ);
        assertThat(testRoute.getSkDauer()).isEqualTo(UPDATED_SK_DAUER);
        assertThat(testRoute.getSkBoogie()).isEqualTo(UPDATED_SK_BOOGIE);
        assertThat(testRoute.getPrmTur()).isEqualTo(DEFAULT_PRM_TUR);
        assertThat(testRoute.getTursperrung()).isEqualTo(UPDATED_TURSPERRUNG);
    }

    @Test
    @Transactional
    void fullUpdateRouteWithPatch() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route using partial update
        Route partialUpdatedRoute = new Route();
        partialUpdatedRoute.setId(route.getId());

        partialUpdatedRoute
            .ort(UPDATED_ORT)
            .entfernung(UPDATED_ENTFERNUNG)
            .haltegrund(UPDATED_HALTEGRUND)
            .gleis(UPDATED_GLEIS)
            .ausstieg(UPDATED_AUSSTIEG)
            .gps(UPDATED_GPS)
            .ansage(UPDATED_ANSAGE)
            .endAnsage(UPDATED_END_ANSAGE)
            .startAnsage(UPDATED_START_ANSAGE)
            .viaAnsage(UPDATED_VIA_ANSAGE)
            .stopAnsage(UPDATED_STOP_ANSAGE)
            .stopAnsageMode(UPDATED_STOP_ANSAGE_MODE)
            .zugziel(UPDATED_ZUGZIEL)
            .entwerter1(UPDATED_ENTWERTER_1)
            .entwerter2(UPDATED_ENTWERTER_2)
            .zoneninfo(UPDATED_ZONENINFO)
            .automat10(UPDATED_AUTOMAT_10)
            .attribute(UPDATED_ATTRIBUTE)
            .sprache1(UPDATED_SPRACHE_1)
            .sprache2(UPDATED_SPRACHE_2)
            .sprache3(UPDATED_SPRACHE_3)
            .kurs(UPDATED_KURS)
            .zieltexte(UPDATED_ZIELTEXTE)
            .liniennummer(UPDATED_LINIENNUMMER)
            .gattung(UPDATED_GATTUNG)
            .lineMarker(UPDATED_LINE_MARKER)
            .ds010(UPDATED_DS_010)
            .linienansage(UPDATED_LINIENANSAGE)
            .spurkranz(UPDATED_SPURKRANZ)
            .skDauer(UPDATED_SK_DAUER)
            .skBoogie(UPDATED_SK_BOOGIE)
            .prmTur(UPDATED_PRM_TUR)
            .tursperrung(UPDATED_TURSPERRUNG);

        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoute))
            )
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testRoute.getEntfernung()).isEqualTo(UPDATED_ENTFERNUNG);
        assertThat(testRoute.getHaltegrund()).isEqualTo(UPDATED_HALTEGRUND);
        assertThat(testRoute.getGleis()).isEqualTo(UPDATED_GLEIS);
        assertThat(testRoute.getAusstieg()).isEqualTo(UPDATED_AUSSTIEG);
        assertThat(testRoute.getGps()).isEqualTo(UPDATED_GPS);
        assertThat(testRoute.getAnsage()).isEqualTo(UPDATED_ANSAGE);
        assertThat(testRoute.getEndAnsage()).isEqualTo(UPDATED_END_ANSAGE);
        assertThat(testRoute.getStartAnsage()).isEqualTo(UPDATED_START_ANSAGE);
        assertThat(testRoute.getViaAnsage()).isEqualTo(UPDATED_VIA_ANSAGE);
        assertThat(testRoute.getStopAnsage()).isEqualTo(UPDATED_STOP_ANSAGE);
        assertThat(testRoute.getStopAnsageMode()).isEqualTo(UPDATED_STOP_ANSAGE_MODE);
        assertThat(testRoute.getZugziel()).isEqualTo(UPDATED_ZUGZIEL);
        assertThat(testRoute.getEntwerter1()).isEqualTo(UPDATED_ENTWERTER_1);
        assertThat(testRoute.getEntwerter2()).isEqualTo(UPDATED_ENTWERTER_2);
        assertThat(testRoute.getZoneninfo()).isEqualTo(UPDATED_ZONENINFO);
        assertThat(testRoute.getAutomat10()).isEqualTo(UPDATED_AUTOMAT_10);
        assertThat(testRoute.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
        assertThat(testRoute.getSprache1()).isEqualTo(UPDATED_SPRACHE_1);
        assertThat(testRoute.getSprache2()).isEqualTo(UPDATED_SPRACHE_2);
        assertThat(testRoute.getSprache3()).isEqualTo(UPDATED_SPRACHE_3);
        assertThat(testRoute.getKurs()).isEqualTo(UPDATED_KURS);
        assertThat(testRoute.getZieltexte()).isEqualTo(UPDATED_ZIELTEXTE);
        assertThat(testRoute.getLiniennummer()).isEqualTo(UPDATED_LINIENNUMMER);
        assertThat(testRoute.getGattung()).isEqualTo(UPDATED_GATTUNG);
        assertThat(testRoute.getLineMarker()).isEqualTo(UPDATED_LINE_MARKER);
        assertThat(testRoute.getDs010()).isEqualTo(UPDATED_DS_010);
        assertThat(testRoute.getLinienansage()).isEqualTo(UPDATED_LINIENANSAGE);
        assertThat(testRoute.getSpurkranz()).isEqualTo(UPDATED_SPURKRANZ);
        assertThat(testRoute.getSkDauer()).isEqualTo(UPDATED_SK_DAUER);
        assertThat(testRoute.getSkBoogie()).isEqualTo(UPDATED_SK_BOOGIE);
        assertThat(testRoute.getPrmTur()).isEqualTo(UPDATED_PRM_TUR);
        assertThat(testRoute.getTursperrung()).isEqualTo(UPDATED_TURSPERRUNG);
    }

    @Test
    @Transactional
    void patchNonExistingRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, route.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(route))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(route))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(route)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeDelete = routeRepository.findAll().size();

        // Delete the route
        restRouteMockMvc
            .perform(delete(ENTITY_API_URL_ID, route.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
