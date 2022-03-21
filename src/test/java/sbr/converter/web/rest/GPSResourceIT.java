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
import sbr.converter.domain.GPS;
import sbr.converter.repository.GPSRepository;

/**
 * Integration tests for the {@link GPSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GPSResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GPS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GPS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INNERES_FENSTER = "AAAAAAAAAA";
    private static final String UPDATED_INNERES_FENSTER = "BBBBBBBBBB";

    private static final String DEFAULT_AU_BERES_FENSTER = "AAAAAAAAAA";
    private static final String UPDATED_AU_BERES_FENSTER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GPSRepository gPSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGPSMockMvc;

    private GPS gPS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GPS createEntity(EntityManager em) {
        GPS gPS = new GPS()
            .name(DEFAULT_NAME)
            .gpsCode(DEFAULT_GPS_CODE)
            .inneresFenster(DEFAULT_INNERES_FENSTER)
            .auBeresFenster(DEFAULT_AU_BERES_FENSTER);
        return gPS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GPS createUpdatedEntity(EntityManager em) {
        GPS gPS = new GPS()
            .name(UPDATED_NAME)
            .gpsCode(UPDATED_GPS_CODE)
            .inneresFenster(UPDATED_INNERES_FENSTER)
            .auBeresFenster(UPDATED_AU_BERES_FENSTER);
        return gPS;
    }

    @BeforeEach
    public void initTest() {
        gPS = createEntity(em);
    }

    @Test
    @Transactional
    void createGPS() throws Exception {
        int databaseSizeBeforeCreate = gPSRepository.findAll().size();
        // Create the GPS
        restGPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gPS)))
            .andExpect(status().isCreated());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeCreate + 1);
        GPS testGPS = gPSList.get(gPSList.size() - 1);
        assertThat(testGPS.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGPS.getGpsCode()).isEqualTo(DEFAULT_GPS_CODE);
        assertThat(testGPS.getInneresFenster()).isEqualTo(DEFAULT_INNERES_FENSTER);
        assertThat(testGPS.getAuBeresFenster()).isEqualTo(DEFAULT_AU_BERES_FENSTER);
    }

    @Test
    @Transactional
    void createGPSWithExistingId() throws Exception {
        // Create the GPS with an existing ID
        gPS.setId(1L);

        int databaseSizeBeforeCreate = gPSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGPSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gPS)))
            .andExpect(status().isBadRequest());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        // Get all the gPSList
        restGPSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gPS.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gpsCode").value(hasItem(DEFAULT_GPS_CODE)))
            .andExpect(jsonPath("$.[*].inneresFenster").value(hasItem(DEFAULT_INNERES_FENSTER)))
            .andExpect(jsonPath("$.[*].auBeresFenster").value(hasItem(DEFAULT_AU_BERES_FENSTER)));
    }

    @Test
    @Transactional
    void getGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        // Get the gPS
        restGPSMockMvc
            .perform(get(ENTITY_API_URL_ID, gPS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gPS.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gpsCode").value(DEFAULT_GPS_CODE))
            .andExpect(jsonPath("$.inneresFenster").value(DEFAULT_INNERES_FENSTER))
            .andExpect(jsonPath("$.auBeresFenster").value(DEFAULT_AU_BERES_FENSTER));
    }

    @Test
    @Transactional
    void getNonExistingGPS() throws Exception {
        // Get the gPS
        restGPSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();

        // Update the gPS
        GPS updatedGPS = gPSRepository.findById(gPS.getId()).get();
        // Disconnect from session so that the updates on updatedGPS are not directly saved in db
        em.detach(updatedGPS);
        updatedGPS
            .name(UPDATED_NAME)
            .gpsCode(UPDATED_GPS_CODE)
            .inneresFenster(UPDATED_INNERES_FENSTER)
            .auBeresFenster(UPDATED_AU_BERES_FENSTER);

        restGPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGPS.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGPS))
            )
            .andExpect(status().isOk());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
        GPS testGPS = gPSList.get(gPSList.size() - 1);
        assertThat(testGPS.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGPS.getGpsCode()).isEqualTo(UPDATED_GPS_CODE);
        assertThat(testGPS.getInneresFenster()).isEqualTo(UPDATED_INNERES_FENSTER);
        assertThat(testGPS.getAuBeresFenster()).isEqualTo(UPDATED_AU_BERES_FENSTER);
    }

    @Test
    @Transactional
    void putNonExistingGPS() throws Exception {
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();
        gPS.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gPS.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGPS() throws Exception {
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();
        gPS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGPSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGPS() throws Exception {
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();
        gPS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGPSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gPS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGPSWithPatch() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();

        // Update the gPS using partial update
        GPS partialUpdatedGPS = new GPS();
        partialUpdatedGPS.setId(gPS.getId());

        partialUpdatedGPS.auBeresFenster(UPDATED_AU_BERES_FENSTER);

        restGPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGPS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGPS))
            )
            .andExpect(status().isOk());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
        GPS testGPS = gPSList.get(gPSList.size() - 1);
        assertThat(testGPS.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGPS.getGpsCode()).isEqualTo(DEFAULT_GPS_CODE);
        assertThat(testGPS.getInneresFenster()).isEqualTo(DEFAULT_INNERES_FENSTER);
        assertThat(testGPS.getAuBeresFenster()).isEqualTo(UPDATED_AU_BERES_FENSTER);
    }

    @Test
    @Transactional
    void fullUpdateGPSWithPatch() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();

        // Update the gPS using partial update
        GPS partialUpdatedGPS = new GPS();
        partialUpdatedGPS.setId(gPS.getId());

        partialUpdatedGPS
            .name(UPDATED_NAME)
            .gpsCode(UPDATED_GPS_CODE)
            .inneresFenster(UPDATED_INNERES_FENSTER)
            .auBeresFenster(UPDATED_AU_BERES_FENSTER);

        restGPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGPS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGPS))
            )
            .andExpect(status().isOk());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
        GPS testGPS = gPSList.get(gPSList.size() - 1);
        assertThat(testGPS.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGPS.getGpsCode()).isEqualTo(UPDATED_GPS_CODE);
        assertThat(testGPS.getInneresFenster()).isEqualTo(UPDATED_INNERES_FENSTER);
        assertThat(testGPS.getAuBeresFenster()).isEqualTo(UPDATED_AU_BERES_FENSTER);
    }

    @Test
    @Transactional
    void patchNonExistingGPS() throws Exception {
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();
        gPS.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gPS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGPS() throws Exception {
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();
        gPS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGPSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gPS))
            )
            .andExpect(status().isBadRequest());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGPS() throws Exception {
        int databaseSizeBeforeUpdate = gPSRepository.findAll().size();
        gPS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGPSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gPS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GPS in the database
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGPS() throws Exception {
        // Initialize the database
        gPSRepository.saveAndFlush(gPS);

        int databaseSizeBeforeDelete = gPSRepository.findAll().size();

        // Delete the gPS
        restGPSMockMvc.perform(delete(ENTITY_API_URL_ID, gPS.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GPS> gPSList = gPSRepository.findAll();
        assertThat(gPSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
