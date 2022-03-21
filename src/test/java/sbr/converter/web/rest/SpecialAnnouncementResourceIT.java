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
import sbr.converter.domain.SpecialAnnouncement;
import sbr.converter.repository.SpecialAnnouncementRepository;

/**
 * Integration tests for the {@link SpecialAnnouncementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecialAnnouncementResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_AUSGABE_ORT = "AAAAAAAAAA";
    private static final String UPDATED_AUSGABE_ORT = "BBBBBBBBBB";

    private static final String DEFAULT_KURZ = "AAAAAAAAAA";
    private static final String UPDATED_KURZ = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ANSAGEDATEI = "AAAAAAAAAA";
    private static final String UPDATED_ANSAGEDATEI = "BBBBBBBBBB";

    private static final String DEFAULT_KLARTEXT = "AAAAAAAAAA";
    private static final String UPDATED_KLARTEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/special-announcements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpecialAnnouncementRepository specialAnnouncementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialAnnouncementMockMvc;

    private SpecialAnnouncement specialAnnouncement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpecialAnnouncement createEntity(EntityManager em) {
        SpecialAnnouncement specialAnnouncement = new SpecialAnnouncement()
            .code(DEFAULT_CODE)
            .item(DEFAULT_ITEM)
            .ausgabeOrt(DEFAULT_AUSGABE_ORT)
            .kurz(DEFAULT_KURZ)
            .language(DEFAULT_LANGUAGE)
            .ansagedatei(DEFAULT_ANSAGEDATEI)
            .klartext(DEFAULT_KLARTEXT);
        return specialAnnouncement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpecialAnnouncement createUpdatedEntity(EntityManager em) {
        SpecialAnnouncement specialAnnouncement = new SpecialAnnouncement()
            .code(UPDATED_CODE)
            .item(UPDATED_ITEM)
            .ausgabeOrt(UPDATED_AUSGABE_ORT)
            .kurz(UPDATED_KURZ)
            .language(UPDATED_LANGUAGE)
            .ansagedatei(UPDATED_ANSAGEDATEI)
            .klartext(UPDATED_KLARTEXT);
        return specialAnnouncement;
    }

    @BeforeEach
    public void initTest() {
        specialAnnouncement = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecialAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = specialAnnouncementRepository.findAll().size();
        // Create the SpecialAnnouncement
        restSpecialAnnouncementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isCreated());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialAnnouncement testSpecialAnnouncement = specialAnnouncementList.get(specialAnnouncementList.size() - 1);
        assertThat(testSpecialAnnouncement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSpecialAnnouncement.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testSpecialAnnouncement.getAusgabeOrt()).isEqualTo(DEFAULT_AUSGABE_ORT);
        assertThat(testSpecialAnnouncement.getKurz()).isEqualTo(DEFAULT_KURZ);
        assertThat(testSpecialAnnouncement.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testSpecialAnnouncement.getAnsagedatei()).isEqualTo(DEFAULT_ANSAGEDATEI);
        assertThat(testSpecialAnnouncement.getKlartext()).isEqualTo(DEFAULT_KLARTEXT);
    }

    @Test
    @Transactional
    void createSpecialAnnouncementWithExistingId() throws Exception {
        // Create the SpecialAnnouncement with an existing ID
        specialAnnouncement.setId(1L);

        int databaseSizeBeforeCreate = specialAnnouncementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialAnnouncementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpecialAnnouncements() throws Exception {
        // Initialize the database
        specialAnnouncementRepository.saveAndFlush(specialAnnouncement);

        // Get all the specialAnnouncementList
        restSpecialAnnouncementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialAnnouncement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].ausgabeOrt").value(hasItem(DEFAULT_AUSGABE_ORT)))
            .andExpect(jsonPath("$.[*].kurz").value(hasItem(DEFAULT_KURZ)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].ansagedatei").value(hasItem(DEFAULT_ANSAGEDATEI)))
            .andExpect(jsonPath("$.[*].klartext").value(hasItem(DEFAULT_KLARTEXT)));
    }

    @Test
    @Transactional
    void getSpecialAnnouncement() throws Exception {
        // Initialize the database
        specialAnnouncementRepository.saveAndFlush(specialAnnouncement);

        // Get the specialAnnouncement
        restSpecialAnnouncementMockMvc
            .perform(get(ENTITY_API_URL_ID, specialAnnouncement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialAnnouncement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM))
            .andExpect(jsonPath("$.ausgabeOrt").value(DEFAULT_AUSGABE_ORT))
            .andExpect(jsonPath("$.kurz").value(DEFAULT_KURZ))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.ansagedatei").value(DEFAULT_ANSAGEDATEI))
            .andExpect(jsonPath("$.klartext").value(DEFAULT_KLARTEXT));
    }

    @Test
    @Transactional
    void getNonExistingSpecialAnnouncement() throws Exception {
        // Get the specialAnnouncement
        restSpecialAnnouncementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSpecialAnnouncement() throws Exception {
        // Initialize the database
        specialAnnouncementRepository.saveAndFlush(specialAnnouncement);

        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();

        // Update the specialAnnouncement
        SpecialAnnouncement updatedSpecialAnnouncement = specialAnnouncementRepository.findById(specialAnnouncement.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialAnnouncement are not directly saved in db
        em.detach(updatedSpecialAnnouncement);
        updatedSpecialAnnouncement
            .code(UPDATED_CODE)
            .item(UPDATED_ITEM)
            .ausgabeOrt(UPDATED_AUSGABE_ORT)
            .kurz(UPDATED_KURZ)
            .language(UPDATED_LANGUAGE)
            .ansagedatei(UPDATED_ANSAGEDATEI)
            .klartext(UPDATED_KLARTEXT);

        restSpecialAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpecialAnnouncement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpecialAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        SpecialAnnouncement testSpecialAnnouncement = specialAnnouncementList.get(specialAnnouncementList.size() - 1);
        assertThat(testSpecialAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSpecialAnnouncement.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testSpecialAnnouncement.getAusgabeOrt()).isEqualTo(UPDATED_AUSGABE_ORT);
        assertThat(testSpecialAnnouncement.getKurz()).isEqualTo(UPDATED_KURZ);
        assertThat(testSpecialAnnouncement.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testSpecialAnnouncement.getAnsagedatei()).isEqualTo(UPDATED_ANSAGEDATEI);
        assertThat(testSpecialAnnouncement.getKlartext()).isEqualTo(UPDATED_KLARTEXT);
    }

    @Test
    @Transactional
    void putNonExistingSpecialAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();
        specialAnnouncement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialAnnouncement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecialAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();
        specialAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecialAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();
        specialAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecialAnnouncementWithPatch() throws Exception {
        // Initialize the database
        specialAnnouncementRepository.saveAndFlush(specialAnnouncement);

        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();

        // Update the specialAnnouncement using partial update
        SpecialAnnouncement partialUpdatedSpecialAnnouncement = new SpecialAnnouncement();
        partialUpdatedSpecialAnnouncement.setId(specialAnnouncement.getId());

        partialUpdatedSpecialAnnouncement
            .code(UPDATED_CODE)
            .ausgabeOrt(UPDATED_AUSGABE_ORT)
            .kurz(UPDATED_KURZ)
            .ansagedatei(UPDATED_ANSAGEDATEI);

        restSpecialAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        SpecialAnnouncement testSpecialAnnouncement = specialAnnouncementList.get(specialAnnouncementList.size() - 1);
        assertThat(testSpecialAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSpecialAnnouncement.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testSpecialAnnouncement.getAusgabeOrt()).isEqualTo(UPDATED_AUSGABE_ORT);
        assertThat(testSpecialAnnouncement.getKurz()).isEqualTo(UPDATED_KURZ);
        assertThat(testSpecialAnnouncement.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testSpecialAnnouncement.getAnsagedatei()).isEqualTo(UPDATED_ANSAGEDATEI);
        assertThat(testSpecialAnnouncement.getKlartext()).isEqualTo(DEFAULT_KLARTEXT);
    }

    @Test
    @Transactional
    void fullUpdateSpecialAnnouncementWithPatch() throws Exception {
        // Initialize the database
        specialAnnouncementRepository.saveAndFlush(specialAnnouncement);

        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();

        // Update the specialAnnouncement using partial update
        SpecialAnnouncement partialUpdatedSpecialAnnouncement = new SpecialAnnouncement();
        partialUpdatedSpecialAnnouncement.setId(specialAnnouncement.getId());

        partialUpdatedSpecialAnnouncement
            .code(UPDATED_CODE)
            .item(UPDATED_ITEM)
            .ausgabeOrt(UPDATED_AUSGABE_ORT)
            .kurz(UPDATED_KURZ)
            .language(UPDATED_LANGUAGE)
            .ansagedatei(UPDATED_ANSAGEDATEI)
            .klartext(UPDATED_KLARTEXT);

        restSpecialAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
        SpecialAnnouncement testSpecialAnnouncement = specialAnnouncementList.get(specialAnnouncementList.size() - 1);
        assertThat(testSpecialAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSpecialAnnouncement.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testSpecialAnnouncement.getAusgabeOrt()).isEqualTo(UPDATED_AUSGABE_ORT);
        assertThat(testSpecialAnnouncement.getKurz()).isEqualTo(UPDATED_KURZ);
        assertThat(testSpecialAnnouncement.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testSpecialAnnouncement.getAnsagedatei()).isEqualTo(UPDATED_ANSAGEDATEI);
        assertThat(testSpecialAnnouncement.getKlartext()).isEqualTo(UPDATED_KLARTEXT);
    }

    @Test
    @Transactional
    void patchNonExistingSpecialAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();
        specialAnnouncement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specialAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecialAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();
        specialAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecialAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = specialAnnouncementRepository.findAll().size();
        specialAnnouncement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialAnnouncement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpecialAnnouncement in the database
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecialAnnouncement() throws Exception {
        // Initialize the database
        specialAnnouncementRepository.saveAndFlush(specialAnnouncement);

        int databaseSizeBeforeDelete = specialAnnouncementRepository.findAll().size();

        // Delete the specialAnnouncement
        restSpecialAnnouncementMockMvc
            .perform(delete(ENTITY_API_URL_ID, specialAnnouncement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpecialAnnouncement> specialAnnouncementList = specialAnnouncementRepository.findAll();
        assertThat(specialAnnouncementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
