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
import sbr.converter.domain.FunctionAnnouncement;
import sbr.converter.repository.FunctionAnnouncementRepository;

/**
 * Integration tests for the {@link FunctionAnnouncementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FunctionAnnouncementResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_AUDIO_FILE = "AAAAAAAAAA";
    private static final String UPDATED_AUDIO_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHREIBUNG = "AAAAAAAAAA";
    private static final String UPDATED_BESCHREIBUNG = "BBBBBBBBBB";

    private static final String DEFAULT_ANMERKUNG = "AAAAAAAAAA";
    private static final String UPDATED_ANMERKUNG = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/function-announcements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FunctionAnnouncementRepository functionAnnouncementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctionAnnouncementMockMvc;

    private FunctionAnnouncement functionAnnouncement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FunctionAnnouncement createEntity(EntityManager em) {
        FunctionAnnouncement functionAnnouncement = new FunctionAnnouncement()
            .code(DEFAULT_CODE)
            .audioFile(DEFAULT_AUDIO_FILE)
            .beschreibung(DEFAULT_BESCHREIBUNG)
            .anmerkung(DEFAULT_ANMERKUNG)
            .language(DEFAULT_LANGUAGE);
        return functionAnnouncement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FunctionAnnouncement createUpdatedEntity(EntityManager em) {
        FunctionAnnouncement functionAnnouncement = new FunctionAnnouncement()
            .code(UPDATED_CODE)
            .audioFile(UPDATED_AUDIO_FILE)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .anmerkung(UPDATED_ANMERKUNG)
            .language(UPDATED_LANGUAGE);
        return functionAnnouncement;
    }

    @BeforeEach
    public void initTest() {
        functionAnnouncement = createEntity(em);
    }

    @Test
    @Transactional
    void createFunctionAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = functionAnnouncementRepository.findAll().size();
        // Create the FunctionAnnouncement
        restFunctionAnnouncementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isCreated());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeCreate + 1);
        FunctionAnnouncement testFunctionAnnouncement = functionAnnouncementList.get(functionAnnouncementList.size() - 1);
        assertThat(testFunctionAnnouncement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFunctionAnnouncement.getAudioFile()).isEqualTo(DEFAULT_AUDIO_FILE);
        assertThat(testFunctionAnnouncement.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
        assertThat(testFunctionAnnouncement.getAnmerkung()).isEqualTo(DEFAULT_ANMERKUNG);
        assertThat(testFunctionAnnouncement.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createFunctionAnnouncementWithExistingId() throws Exception {
        // Create the FunctionAnnouncement with an existing ID
        functionAnnouncement.setId(1L);

        int databaseSizeBeforeCreate = functionAnnouncementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctionAnnouncementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFunctionAnnouncements() throws Exception {
        // Initialize the database
        functionAnnouncementRepository.saveAndFlush(functionAnnouncement);

        // Get all the functionAnnouncementList
        restFunctionAnnouncementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functionAnnouncement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].audioFile").value(hasItem(DEFAULT_AUDIO_FILE)))
            .andExpect(jsonPath("$.[*].beschreibung").value(hasItem(DEFAULT_BESCHREIBUNG)))
            .andExpect(jsonPath("$.[*].anmerkung").value(hasItem(DEFAULT_ANMERKUNG)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getFunctionAnnouncement() throws Exception {
        // Initialize the database
        functionAnnouncementRepository.saveAndFlush(functionAnnouncement);

        // Get the functionAnnouncement
        restFunctionAnnouncementMockMvc
            .perform(get(ENTITY_API_URL_ID, functionAnnouncement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functionAnnouncement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.audioFile").value(DEFAULT_AUDIO_FILE))
            .andExpect(jsonPath("$.beschreibung").value(DEFAULT_BESCHREIBUNG))
            .andExpect(jsonPath("$.anmerkung").value(DEFAULT_ANMERKUNG))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getNonExistingFunctionAnnouncement() throws Exception {
        // Get the functionAnnouncement
        restFunctionAnnouncementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFunctionAnnouncement() throws Exception {
        // Initialize the database
        functionAnnouncementRepository.saveAndFlush(functionAnnouncement);

        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();

        // Update the functionAnnouncement
        FunctionAnnouncement updatedFunctionAnnouncement = functionAnnouncementRepository.findById(functionAnnouncement.getId()).get();
        // Disconnect from session so that the updates on updatedFunctionAnnouncement are not directly saved in db
        em.detach(updatedFunctionAnnouncement);
        updatedFunctionAnnouncement
            .code(UPDATED_CODE)
            .audioFile(UPDATED_AUDIO_FILE)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .anmerkung(UPDATED_ANMERKUNG)
            .language(UPDATED_LANGUAGE);

        restFunctionAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFunctionAnnouncement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFunctionAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        FunctionAnnouncement testFunctionAnnouncement = functionAnnouncementList.get(functionAnnouncementList.size() - 1);
        assertThat(testFunctionAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFunctionAnnouncement.getAudioFile()).isEqualTo(UPDATED_AUDIO_FILE);
        assertThat(testFunctionAnnouncement.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
        assertThat(testFunctionAnnouncement.getAnmerkung()).isEqualTo(UPDATED_ANMERKUNG);
        assertThat(testFunctionAnnouncement.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingFunctionAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();
        functionAnnouncement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, functionAnnouncement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunctionAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();
        functionAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunctionAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();
        functionAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFunctionAnnouncementWithPatch() throws Exception {
        // Initialize the database
        functionAnnouncementRepository.saveAndFlush(functionAnnouncement);

        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();

        // Update the functionAnnouncement using partial update
        FunctionAnnouncement partialUpdatedFunctionAnnouncement = new FunctionAnnouncement();
        partialUpdatedFunctionAnnouncement.setId(functionAnnouncement.getId());

        partialUpdatedFunctionAnnouncement.code(UPDATED_CODE).beschreibung(UPDATED_BESCHREIBUNG).language(UPDATED_LANGUAGE);

        restFunctionAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctionAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctionAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        FunctionAnnouncement testFunctionAnnouncement = functionAnnouncementList.get(functionAnnouncementList.size() - 1);
        assertThat(testFunctionAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFunctionAnnouncement.getAudioFile()).isEqualTo(DEFAULT_AUDIO_FILE);
        assertThat(testFunctionAnnouncement.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
        assertThat(testFunctionAnnouncement.getAnmerkung()).isEqualTo(DEFAULT_ANMERKUNG);
        assertThat(testFunctionAnnouncement.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateFunctionAnnouncementWithPatch() throws Exception {
        // Initialize the database
        functionAnnouncementRepository.saveAndFlush(functionAnnouncement);

        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();

        // Update the functionAnnouncement using partial update
        FunctionAnnouncement partialUpdatedFunctionAnnouncement = new FunctionAnnouncement();
        partialUpdatedFunctionAnnouncement.setId(functionAnnouncement.getId());

        partialUpdatedFunctionAnnouncement
            .code(UPDATED_CODE)
            .audioFile(UPDATED_AUDIO_FILE)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .anmerkung(UPDATED_ANMERKUNG)
            .language(UPDATED_LANGUAGE);

        restFunctionAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctionAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctionAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        FunctionAnnouncement testFunctionAnnouncement = functionAnnouncementList.get(functionAnnouncementList.size() - 1);
        assertThat(testFunctionAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFunctionAnnouncement.getAudioFile()).isEqualTo(UPDATED_AUDIO_FILE);
        assertThat(testFunctionAnnouncement.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
        assertThat(testFunctionAnnouncement.getAnmerkung()).isEqualTo(UPDATED_ANMERKUNG);
        assertThat(testFunctionAnnouncement.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingFunctionAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();
        functionAnnouncement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, functionAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunctionAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();
        functionAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunctionAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = functionAnnouncementRepository.findAll().size();
        functionAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionAnnouncement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FunctionAnnouncement in the database
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunctionAnnouncement() throws Exception {
        // Initialize the database
        functionAnnouncementRepository.saveAndFlush(functionAnnouncement);

        int databaseSizeBeforeDelete = functionAnnouncementRepository.findAll().size();

        // Delete the functionAnnouncement
        restFunctionAnnouncementMockMvc
            .perform(delete(ENTITY_API_URL_ID, functionAnnouncement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FunctionAnnouncement> functionAnnouncementList = functionAnnouncementRepository.findAll();
        assertThat(functionAnnouncementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
