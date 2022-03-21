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
import sbr.converter.domain.Sonderziele;
import sbr.converter.repository.SonderzieleRepository;

/**
 * Integration tests for the {@link SonderzieleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SonderzieleResourceIT {

    private static final String DEFAULT_ZUGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_ZUGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT = "AAAAAAAAAA";
    private static final String UPDATED_FRONT = "BBBBBBBBBB";

    private static final String DEFAULT_SEITE_1 = "AAAAAAAAAA";
    private static final String UPDATED_SEITE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SEITE_2 = "AAAAAAAAAA";
    private static final String UPDATED_SEITE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_INNEN = "AAAAAAAAAA";
    private static final String UPDATED_INNEN = "BBBBBBBBBB";

    private static final String DEFAULT_TFT = "AAAAAAAAAA";
    private static final String UPDATED_TFT = "BBBBBBBBBB";

    private static final String DEFAULT_TERMINAL = "AAAAAAAAAA";
    private static final String UPDATED_TERMINAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sonderzieles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SonderzieleRepository sonderzieleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSonderzieleMockMvc;

    private Sonderziele sonderziele;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sonderziele createEntity(EntityManager em) {
        Sonderziele sonderziele = new Sonderziele()
            .zugnummer(DEFAULT_ZUGNUMMER)
            .front(DEFAULT_FRONT)
            .seite1(DEFAULT_SEITE_1)
            .seite2(DEFAULT_SEITE_2)
            .innen(DEFAULT_INNEN)
            .tft(DEFAULT_TFT)
            .terminal(DEFAULT_TERMINAL);
        return sonderziele;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sonderziele createUpdatedEntity(EntityManager em) {
        Sonderziele sonderziele = new Sonderziele()
            .zugnummer(UPDATED_ZUGNUMMER)
            .front(UPDATED_FRONT)
            .seite1(UPDATED_SEITE_1)
            .seite2(UPDATED_SEITE_2)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL);
        return sonderziele;
    }

    @BeforeEach
    public void initTest() {
        sonderziele = createEntity(em);
    }

    @Test
    @Transactional
    void createSonderziele() throws Exception {
        int databaseSizeBeforeCreate = sonderzieleRepository.findAll().size();
        // Create the Sonderziele
        restSonderzieleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sonderziele)))
            .andExpect(status().isCreated());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeCreate + 1);
        Sonderziele testSonderziele = sonderzieleList.get(sonderzieleList.size() - 1);
        assertThat(testSonderziele.getZugnummer()).isEqualTo(DEFAULT_ZUGNUMMER);
        assertThat(testSonderziele.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testSonderziele.getSeite1()).isEqualTo(DEFAULT_SEITE_1);
        assertThat(testSonderziele.getSeite2()).isEqualTo(DEFAULT_SEITE_2);
        assertThat(testSonderziele.getInnen()).isEqualTo(DEFAULT_INNEN);
        assertThat(testSonderziele.getTft()).isEqualTo(DEFAULT_TFT);
        assertThat(testSonderziele.getTerminal()).isEqualTo(DEFAULT_TERMINAL);
    }

    @Test
    @Transactional
    void createSonderzieleWithExistingId() throws Exception {
        // Create the Sonderziele with an existing ID
        sonderziele.setId(1L);

        int databaseSizeBeforeCreate = sonderzieleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSonderzieleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sonderziele)))
            .andExpect(status().isBadRequest());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSonderzieles() throws Exception {
        // Initialize the database
        sonderzieleRepository.saveAndFlush(sonderziele);

        // Get all the sonderzieleList
        restSonderzieleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sonderziele.getId().intValue())))
            .andExpect(jsonPath("$.[*].zugnummer").value(hasItem(DEFAULT_ZUGNUMMER)))
            .andExpect(jsonPath("$.[*].front").value(hasItem(DEFAULT_FRONT)))
            .andExpect(jsonPath("$.[*].seite1").value(hasItem(DEFAULT_SEITE_1)))
            .andExpect(jsonPath("$.[*].seite2").value(hasItem(DEFAULT_SEITE_2)))
            .andExpect(jsonPath("$.[*].innen").value(hasItem(DEFAULT_INNEN)))
            .andExpect(jsonPath("$.[*].tft").value(hasItem(DEFAULT_TFT)))
            .andExpect(jsonPath("$.[*].terminal").value(hasItem(DEFAULT_TERMINAL)));
    }

    @Test
    @Transactional
    void getSonderziele() throws Exception {
        // Initialize the database
        sonderzieleRepository.saveAndFlush(sonderziele);

        // Get the sonderziele
        restSonderzieleMockMvc
            .perform(get(ENTITY_API_URL_ID, sonderziele.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sonderziele.getId().intValue()))
            .andExpect(jsonPath("$.zugnummer").value(DEFAULT_ZUGNUMMER))
            .andExpect(jsonPath("$.front").value(DEFAULT_FRONT))
            .andExpect(jsonPath("$.seite1").value(DEFAULT_SEITE_1))
            .andExpect(jsonPath("$.seite2").value(DEFAULT_SEITE_2))
            .andExpect(jsonPath("$.innen").value(DEFAULT_INNEN))
            .andExpect(jsonPath("$.tft").value(DEFAULT_TFT))
            .andExpect(jsonPath("$.terminal").value(DEFAULT_TERMINAL));
    }

    @Test
    @Transactional
    void getNonExistingSonderziele() throws Exception {
        // Get the sonderziele
        restSonderzieleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSonderziele() throws Exception {
        // Initialize the database
        sonderzieleRepository.saveAndFlush(sonderziele);

        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();

        // Update the sonderziele
        Sonderziele updatedSonderziele = sonderzieleRepository.findById(sonderziele.getId()).get();
        // Disconnect from session so that the updates on updatedSonderziele are not directly saved in db
        em.detach(updatedSonderziele);
        updatedSonderziele
            .zugnummer(UPDATED_ZUGNUMMER)
            .front(UPDATED_FRONT)
            .seite1(UPDATED_SEITE_1)
            .seite2(UPDATED_SEITE_2)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL);

        restSonderzieleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSonderziele.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSonderziele))
            )
            .andExpect(status().isOk());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
        Sonderziele testSonderziele = sonderzieleList.get(sonderzieleList.size() - 1);
        assertThat(testSonderziele.getZugnummer()).isEqualTo(UPDATED_ZUGNUMMER);
        assertThat(testSonderziele.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testSonderziele.getSeite1()).isEqualTo(UPDATED_SEITE_1);
        assertThat(testSonderziele.getSeite2()).isEqualTo(UPDATED_SEITE_2);
        assertThat(testSonderziele.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testSonderziele.getTft()).isEqualTo(UPDATED_TFT);
        assertThat(testSonderziele.getTerminal()).isEqualTo(UPDATED_TERMINAL);
    }

    @Test
    @Transactional
    void putNonExistingSonderziele() throws Exception {
        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();
        sonderziele.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSonderzieleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sonderziele.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sonderziele))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSonderziele() throws Exception {
        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();
        sonderziele.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSonderzieleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sonderziele))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSonderziele() throws Exception {
        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();
        sonderziele.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSonderzieleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sonderziele)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSonderzieleWithPatch() throws Exception {
        // Initialize the database
        sonderzieleRepository.saveAndFlush(sonderziele);

        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();

        // Update the sonderziele using partial update
        Sonderziele partialUpdatedSonderziele = new Sonderziele();
        partialUpdatedSonderziele.setId(sonderziele.getId());

        partialUpdatedSonderziele.zugnummer(UPDATED_ZUGNUMMER).innen(UPDATED_INNEN).terminal(UPDATED_TERMINAL);

        restSonderzieleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSonderziele.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSonderziele))
            )
            .andExpect(status().isOk());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
        Sonderziele testSonderziele = sonderzieleList.get(sonderzieleList.size() - 1);
        assertThat(testSonderziele.getZugnummer()).isEqualTo(UPDATED_ZUGNUMMER);
        assertThat(testSonderziele.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testSonderziele.getSeite1()).isEqualTo(DEFAULT_SEITE_1);
        assertThat(testSonderziele.getSeite2()).isEqualTo(DEFAULT_SEITE_2);
        assertThat(testSonderziele.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testSonderziele.getTft()).isEqualTo(DEFAULT_TFT);
        assertThat(testSonderziele.getTerminal()).isEqualTo(UPDATED_TERMINAL);
    }

    @Test
    @Transactional
    void fullUpdateSonderzieleWithPatch() throws Exception {
        // Initialize the database
        sonderzieleRepository.saveAndFlush(sonderziele);

        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();

        // Update the sonderziele using partial update
        Sonderziele partialUpdatedSonderziele = new Sonderziele();
        partialUpdatedSonderziele.setId(sonderziele.getId());

        partialUpdatedSonderziele
            .zugnummer(UPDATED_ZUGNUMMER)
            .front(UPDATED_FRONT)
            .seite1(UPDATED_SEITE_1)
            .seite2(UPDATED_SEITE_2)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL);

        restSonderzieleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSonderziele.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSonderziele))
            )
            .andExpect(status().isOk());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
        Sonderziele testSonderziele = sonderzieleList.get(sonderzieleList.size() - 1);
        assertThat(testSonderziele.getZugnummer()).isEqualTo(UPDATED_ZUGNUMMER);
        assertThat(testSonderziele.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testSonderziele.getSeite1()).isEqualTo(UPDATED_SEITE_1);
        assertThat(testSonderziele.getSeite2()).isEqualTo(UPDATED_SEITE_2);
        assertThat(testSonderziele.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testSonderziele.getTft()).isEqualTo(UPDATED_TFT);
        assertThat(testSonderziele.getTerminal()).isEqualTo(UPDATED_TERMINAL);
    }

    @Test
    @Transactional
    void patchNonExistingSonderziele() throws Exception {
        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();
        sonderziele.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSonderzieleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sonderziele.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sonderziele))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSonderziele() throws Exception {
        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();
        sonderziele.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSonderzieleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sonderziele))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSonderziele() throws Exception {
        int databaseSizeBeforeUpdate = sonderzieleRepository.findAll().size();
        sonderziele.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSonderzieleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sonderziele))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sonderziele in the database
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSonderziele() throws Exception {
        // Initialize the database
        sonderzieleRepository.saveAndFlush(sonderziele);

        int databaseSizeBeforeDelete = sonderzieleRepository.findAll().size();

        // Delete the sonderziele
        restSonderzieleMockMvc
            .perform(delete(ENTITY_API_URL_ID, sonderziele.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sonderziele> sonderzieleList = sonderzieleRepository.findAll();
        assertThat(sonderzieleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
