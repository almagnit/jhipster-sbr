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
import sbr.converter.domain.Announcement;
import sbr.converter.repository.AnnouncementRepository;

/**
 * Integration tests for the {@link AnnouncementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnnouncementResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ANSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ANSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_ANNOUNCEMENTFILE = "AAAAAAAAAA";
    private static final String UPDATED_ANNOUNCEMENTFILE = "BBBBBBBBBB";

    private static final String DEFAULT_KLARTEXT = "AAAAAAAAAA";
    private static final String UPDATED_KLARTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/announcements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnouncementMockMvc;

    private Announcement announcement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .code(DEFAULT_CODE)
            .ansage(DEFAULT_ANSAGE)
            .item(DEFAULT_ITEM)
            .announcementfile(DEFAULT_ANNOUNCEMENTFILE)
            .klartext(DEFAULT_KLARTEXT)
            .language(DEFAULT_LANGUAGE);
        return announcement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createUpdatedEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .code(UPDATED_CODE)
            .ansage(UPDATED_ANSAGE)
            .item(UPDATED_ITEM)
            .announcementfile(UPDATED_ANNOUNCEMENTFILE)
            .klartext(UPDATED_KLARTEXT)
            .language(UPDATED_LANGUAGE);
        return announcement;
    }

    @BeforeEach
    public void initTest() {
        announcement = createEntity(em);
    }

    @Test
    @Transactional
    void createAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = announcementRepository.findAll().size();
        // Create the Announcement
        restAnnouncementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isCreated());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate + 1);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAnnouncement.getAnsage()).isEqualTo(DEFAULT_ANSAGE);
        assertThat(testAnnouncement.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testAnnouncement.getAnnouncementfile()).isEqualTo(DEFAULT_ANNOUNCEMENTFILE);
        assertThat(testAnnouncement.getKlartext()).isEqualTo(DEFAULT_KLARTEXT);
        assertThat(testAnnouncement.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createAnnouncementWithExistingId() throws Exception {
        // Create the Announcement with an existing ID
        announcement.setId(1L);

        int databaseSizeBeforeCreate = announcementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnouncementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnnouncements() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList
        restAnnouncementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(announcement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].ansage").value(hasItem(DEFAULT_ANSAGE)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].announcementfile").value(hasItem(DEFAULT_ANNOUNCEMENTFILE)))
            .andExpect(jsonPath("$.[*].klartext").value(hasItem(DEFAULT_KLARTEXT)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get the announcement
        restAnnouncementMockMvc
            .perform(get(ENTITY_API_URL_ID, announcement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(announcement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.ansage").value(DEFAULT_ANSAGE))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM))
            .andExpect(jsonPath("$.announcementfile").value(DEFAULT_ANNOUNCEMENTFILE))
            .andExpect(jsonPath("$.klartext").value(DEFAULT_KLARTEXT))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getNonExistingAnnouncement() throws Exception {
        // Get the announcement
        restAnnouncementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement
        Announcement updatedAnnouncement = announcementRepository.findById(announcement.getId()).get();
        // Disconnect from session so that the updates on updatedAnnouncement are not directly saved in db
        em.detach(updatedAnnouncement);
        updatedAnnouncement
            .code(UPDATED_CODE)
            .ansage(UPDATED_ANSAGE)
            .item(UPDATED_ITEM)
            .announcementfile(UPDATED_ANNOUNCEMENTFILE)
            .klartext(UPDATED_KLARTEXT)
            .language(UPDATED_LANGUAGE);

        restAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnnouncement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAnnouncement.getAnsage()).isEqualTo(UPDATED_ANSAGE);
        assertThat(testAnnouncement.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testAnnouncement.getAnnouncementfile()).isEqualTo(UPDATED_ANNOUNCEMENTFILE);
        assertThat(testAnnouncement.getKlartext()).isEqualTo(UPDATED_KLARTEXT);
        assertThat(testAnnouncement.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, announcement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnnouncementWithPatch() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement using partial update
        Announcement partialUpdatedAnnouncement = new Announcement();
        partialUpdatedAnnouncement.setId(announcement.getId());

        partialUpdatedAnnouncement.code(UPDATED_CODE).announcementfile(UPDATED_ANNOUNCEMENTFILE);

        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAnnouncement.getAnsage()).isEqualTo(DEFAULT_ANSAGE);
        assertThat(testAnnouncement.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testAnnouncement.getAnnouncementfile()).isEqualTo(UPDATED_ANNOUNCEMENTFILE);
        assertThat(testAnnouncement.getKlartext()).isEqualTo(DEFAULT_KLARTEXT);
        assertThat(testAnnouncement.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateAnnouncementWithPatch() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement using partial update
        Announcement partialUpdatedAnnouncement = new Announcement();
        partialUpdatedAnnouncement.setId(announcement.getId());

        partialUpdatedAnnouncement
            .code(UPDATED_CODE)
            .ansage(UPDATED_ANSAGE)
            .item(UPDATED_ITEM)
            .announcementfile(UPDATED_ANNOUNCEMENTFILE)
            .klartext(UPDATED_KLARTEXT)
            .language(UPDATED_LANGUAGE);

        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAnnouncement.getAnsage()).isEqualTo(UPDATED_ANSAGE);
        assertThat(testAnnouncement.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testAnnouncement.getAnnouncementfile()).isEqualTo(UPDATED_ANNOUNCEMENTFILE);
        assertThat(testAnnouncement.getKlartext()).isEqualTo(UPDATED_KLARTEXT);
        assertThat(testAnnouncement.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, announcement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeDelete = announcementRepository.findAll().size();

        // Delete the announcement
        restAnnouncementMockMvc
            .perform(delete(ENTITY_API_URL_ID, announcement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
