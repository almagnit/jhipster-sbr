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
import sbr.converter.domain.Ort;
import sbr.converter.repository.OrtRepository;

/**
 * Integration tests for the {@link OrtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrtResourceIT {

    private static final String DEFAULT_IBNR = "AAAAAAAAAA";
    private static final String UPDATED_IBNR = "BBBBBBBBBB";

    private static final String DEFAULT_DS_100 = "AAAAAAAAAA";
    private static final String UPDATED_DS_100 = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT = "AAAAAAAAAA";
    private static final String UPDATED_FRONT = "BBBBBBBBBB";

    private static final String DEFAULT_SEITE = "AAAAAAAAAA";
    private static final String UPDATED_SEITE = "BBBBBBBBBB";

    private static final String DEFAULT_INNEN = "AAAAAAAAAA";
    private static final String UPDATED_INNEN = "BBBBBBBBBB";

    private static final String DEFAULT_TFT = "AAAAAAAAAA";
    private static final String UPDATED_TFT = "BBBBBBBBBB";

    private static final String DEFAULT_TERMINAL = "AAAAAAAAAA";
    private static final String UPDATED_TERMINAL = "BBBBBBBBBB";

    private static final String DEFAULT_DS_001_C = "AAAAAAAAAA";
    private static final String UPDATED_DS_001_C = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO = "BBBBBBBBBB";

    private static final String DEFAULT_DS_009 = "AAAAAAAAAA";
    private static final String UPDATED_DS_009 = "BBBBBBBBBB";

    private static final String DEFAULT_DS_003 = "AAAAAAAAAA";
    private static final String UPDATED_DS_003 = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/orts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrtRepository ortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrtMockMvc;

    private Ort ort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ort createEntity(EntityManager em) {
        Ort ort = new Ort()
            .ibnr(DEFAULT_IBNR)
            .ds100(DEFAULT_DS_100)
            .front(DEFAULT_FRONT)
            .seite(DEFAULT_SEITE)
            .innen(DEFAULT_INNEN)
            .tft(DEFAULT_TFT)
            .terminal(DEFAULT_TERMINAL)
            .ds001c(DEFAULT_DS_001_C)
            .video(DEFAULT_VIDEO)
            .ds009(DEFAULT_DS_009)
            .ds003(DEFAULT_DS_003)
            .language(DEFAULT_LANGUAGE);
        return ort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ort createUpdatedEntity(EntityManager em) {
        Ort ort = new Ort()
            .ibnr(UPDATED_IBNR)
            .ds100(UPDATED_DS_100)
            .front(UPDATED_FRONT)
            .seite(UPDATED_SEITE)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL)
            .ds001c(UPDATED_DS_001_C)
            .video(UPDATED_VIDEO)
            .ds009(UPDATED_DS_009)
            .ds003(UPDATED_DS_003)
            .language(UPDATED_LANGUAGE);
        return ort;
    }

    @BeforeEach
    public void initTest() {
        ort = createEntity(em);
    }

    @Test
    @Transactional
    void createOrt() throws Exception {
        int databaseSizeBeforeCreate = ortRepository.findAll().size();
        // Create the Ort
        restOrtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ort)))
            .andExpect(status().isCreated());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeCreate + 1);
        Ort testOrt = ortList.get(ortList.size() - 1);
        assertThat(testOrt.getIbnr()).isEqualTo(DEFAULT_IBNR);
        assertThat(testOrt.getDs100()).isEqualTo(DEFAULT_DS_100);
        assertThat(testOrt.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testOrt.getSeite()).isEqualTo(DEFAULT_SEITE);
        assertThat(testOrt.getInnen()).isEqualTo(DEFAULT_INNEN);
        assertThat(testOrt.getTft()).isEqualTo(DEFAULT_TFT);
        assertThat(testOrt.getTerminal()).isEqualTo(DEFAULT_TERMINAL);
        assertThat(testOrt.getDs001c()).isEqualTo(DEFAULT_DS_001_C);
        assertThat(testOrt.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testOrt.getDs009()).isEqualTo(DEFAULT_DS_009);
        assertThat(testOrt.getDs003()).isEqualTo(DEFAULT_DS_003);
        assertThat(testOrt.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createOrtWithExistingId() throws Exception {
        // Create the Ort with an existing ID
        ort.setId(1L);

        int databaseSizeBeforeCreate = ortRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ort)))
            .andExpect(status().isBadRequest());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrts() throws Exception {
        // Initialize the database
        ortRepository.saveAndFlush(ort);

        // Get all the ortList
        restOrtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ort.getId().intValue())))
            .andExpect(jsonPath("$.[*].ibnr").value(hasItem(DEFAULT_IBNR)))
            .andExpect(jsonPath("$.[*].ds100").value(hasItem(DEFAULT_DS_100)))
            .andExpect(jsonPath("$.[*].front").value(hasItem(DEFAULT_FRONT)))
            .andExpect(jsonPath("$.[*].seite").value(hasItem(DEFAULT_SEITE)))
            .andExpect(jsonPath("$.[*].innen").value(hasItem(DEFAULT_INNEN)))
            .andExpect(jsonPath("$.[*].tft").value(hasItem(DEFAULT_TFT)))
            .andExpect(jsonPath("$.[*].terminal").value(hasItem(DEFAULT_TERMINAL)))
            .andExpect(jsonPath("$.[*].ds001c").value(hasItem(DEFAULT_DS_001_C)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].ds009").value(hasItem(DEFAULT_DS_009)))
            .andExpect(jsonPath("$.[*].ds003").value(hasItem(DEFAULT_DS_003)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getOrt() throws Exception {
        // Initialize the database
        ortRepository.saveAndFlush(ort);

        // Get the ort
        restOrtMockMvc
            .perform(get(ENTITY_API_URL_ID, ort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ort.getId().intValue()))
            .andExpect(jsonPath("$.ibnr").value(DEFAULT_IBNR))
            .andExpect(jsonPath("$.ds100").value(DEFAULT_DS_100))
            .andExpect(jsonPath("$.front").value(DEFAULT_FRONT))
            .andExpect(jsonPath("$.seite").value(DEFAULT_SEITE))
            .andExpect(jsonPath("$.innen").value(DEFAULT_INNEN))
            .andExpect(jsonPath("$.tft").value(DEFAULT_TFT))
            .andExpect(jsonPath("$.terminal").value(DEFAULT_TERMINAL))
            .andExpect(jsonPath("$.ds001c").value(DEFAULT_DS_001_C))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO))
            .andExpect(jsonPath("$.ds009").value(DEFAULT_DS_009))
            .andExpect(jsonPath("$.ds003").value(DEFAULT_DS_003))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getNonExistingOrt() throws Exception {
        // Get the ort
        restOrtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrt() throws Exception {
        // Initialize the database
        ortRepository.saveAndFlush(ort);

        int databaseSizeBeforeUpdate = ortRepository.findAll().size();

        // Update the ort
        Ort updatedOrt = ortRepository.findById(ort.getId()).get();
        // Disconnect from session so that the updates on updatedOrt are not directly saved in db
        em.detach(updatedOrt);
        updatedOrt
            .ibnr(UPDATED_IBNR)
            .ds100(UPDATED_DS_100)
            .front(UPDATED_FRONT)
            .seite(UPDATED_SEITE)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL)
            .ds001c(UPDATED_DS_001_C)
            .video(UPDATED_VIDEO)
            .ds009(UPDATED_DS_009)
            .ds003(UPDATED_DS_003)
            .language(UPDATED_LANGUAGE);

        restOrtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrt))
            )
            .andExpect(status().isOk());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
        Ort testOrt = ortList.get(ortList.size() - 1);
        assertThat(testOrt.getIbnr()).isEqualTo(UPDATED_IBNR);
        assertThat(testOrt.getDs100()).isEqualTo(UPDATED_DS_100);
        assertThat(testOrt.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testOrt.getSeite()).isEqualTo(UPDATED_SEITE);
        assertThat(testOrt.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testOrt.getTft()).isEqualTo(UPDATED_TFT);
        assertThat(testOrt.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testOrt.getDs001c()).isEqualTo(UPDATED_DS_001_C);
        assertThat(testOrt.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testOrt.getDs009()).isEqualTo(UPDATED_DS_009);
        assertThat(testOrt.getDs003()).isEqualTo(UPDATED_DS_003);
        assertThat(testOrt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingOrt() throws Exception {
        int databaseSizeBeforeUpdate = ortRepository.findAll().size();
        ort.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ort.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrt() throws Exception {
        int databaseSizeBeforeUpdate = ortRepository.findAll().size();
        ort.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrt() throws Exception {
        int databaseSizeBeforeUpdate = ortRepository.findAll().size();
        ort.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrtWithPatch() throws Exception {
        // Initialize the database
        ortRepository.saveAndFlush(ort);

        int databaseSizeBeforeUpdate = ortRepository.findAll().size();

        // Update the ort using partial update
        Ort partialUpdatedOrt = new Ort();
        partialUpdatedOrt.setId(ort.getId());

        partialUpdatedOrt.ds100(UPDATED_DS_100).innen(UPDATED_INNEN).tft(UPDATED_TFT).ds001c(UPDATED_DS_001_C);

        restOrtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrt))
            )
            .andExpect(status().isOk());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
        Ort testOrt = ortList.get(ortList.size() - 1);
        assertThat(testOrt.getIbnr()).isEqualTo(DEFAULT_IBNR);
        assertThat(testOrt.getDs100()).isEqualTo(UPDATED_DS_100);
        assertThat(testOrt.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testOrt.getSeite()).isEqualTo(DEFAULT_SEITE);
        assertThat(testOrt.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testOrt.getTft()).isEqualTo(UPDATED_TFT);
        assertThat(testOrt.getTerminal()).isEqualTo(DEFAULT_TERMINAL);
        assertThat(testOrt.getDs001c()).isEqualTo(UPDATED_DS_001_C);
        assertThat(testOrt.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testOrt.getDs009()).isEqualTo(DEFAULT_DS_009);
        assertThat(testOrt.getDs003()).isEqualTo(DEFAULT_DS_003);
        assertThat(testOrt.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateOrtWithPatch() throws Exception {
        // Initialize the database
        ortRepository.saveAndFlush(ort);

        int databaseSizeBeforeUpdate = ortRepository.findAll().size();

        // Update the ort using partial update
        Ort partialUpdatedOrt = new Ort();
        partialUpdatedOrt.setId(ort.getId());

        partialUpdatedOrt
            .ibnr(UPDATED_IBNR)
            .ds100(UPDATED_DS_100)
            .front(UPDATED_FRONT)
            .seite(UPDATED_SEITE)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL)
            .ds001c(UPDATED_DS_001_C)
            .video(UPDATED_VIDEO)
            .ds009(UPDATED_DS_009)
            .ds003(UPDATED_DS_003)
            .language(UPDATED_LANGUAGE);

        restOrtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrt))
            )
            .andExpect(status().isOk());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
        Ort testOrt = ortList.get(ortList.size() - 1);
        assertThat(testOrt.getIbnr()).isEqualTo(UPDATED_IBNR);
        assertThat(testOrt.getDs100()).isEqualTo(UPDATED_DS_100);
        assertThat(testOrt.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testOrt.getSeite()).isEqualTo(UPDATED_SEITE);
        assertThat(testOrt.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testOrt.getTft()).isEqualTo(UPDATED_TFT);
        assertThat(testOrt.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testOrt.getDs001c()).isEqualTo(UPDATED_DS_001_C);
        assertThat(testOrt.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testOrt.getDs009()).isEqualTo(UPDATED_DS_009);
        assertThat(testOrt.getDs003()).isEqualTo(UPDATED_DS_003);
        assertThat(testOrt.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingOrt() throws Exception {
        int databaseSizeBeforeUpdate = ortRepository.findAll().size();
        ort.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ort.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrt() throws Exception {
        int databaseSizeBeforeUpdate = ortRepository.findAll().size();
        ort.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrt() throws Exception {
        int databaseSizeBeforeUpdate = ortRepository.findAll().size();
        ort.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrtMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ort in the database
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrt() throws Exception {
        // Initialize the database
        ortRepository.saveAndFlush(ort);

        int databaseSizeBeforeDelete = ortRepository.findAll().size();

        // Delete the ort
        restOrtMockMvc.perform(delete(ENTITY_API_URL_ID, ort.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ort> ortList = ortRepository.findAll();
        assertThat(ortList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
