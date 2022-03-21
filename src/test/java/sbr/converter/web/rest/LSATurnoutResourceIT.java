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
import sbr.converter.domain.LSATurnout;
import sbr.converter.repository.LSATurnoutRepository;

/**
 * Integration tests for the {@link LSATurnoutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LSATurnoutResourceIT {

    private static final String DEFAULT_STATION_1 = "AAAAAAAAAA";
    private static final String UPDATED_STATION_1 = "BBBBBBBBBB";

    private static final String DEFAULT_STATION_2 = "AAAAAAAAAA";
    private static final String UPDATED_STATION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LSA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LSA_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TURNOUT = "AAAAAAAAAA";
    private static final String UPDATED_TURNOUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lsa-turnouts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LSATurnoutRepository lSATurnoutRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLSATurnoutMockMvc;

    private LSATurnout lSATurnout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LSATurnout createEntity(EntityManager em) {
        LSATurnout lSATurnout = new LSATurnout()
            .station1(DEFAULT_STATION_1)
            .station2(DEFAULT_STATION_2)
            .lsaCode(DEFAULT_LSA_CODE)
            .turnout(DEFAULT_TURNOUT);
        return lSATurnout;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LSATurnout createUpdatedEntity(EntityManager em) {
        LSATurnout lSATurnout = new LSATurnout()
            .station1(UPDATED_STATION_1)
            .station2(UPDATED_STATION_2)
            .lsaCode(UPDATED_LSA_CODE)
            .turnout(UPDATED_TURNOUT);
        return lSATurnout;
    }

    @BeforeEach
    public void initTest() {
        lSATurnout = createEntity(em);
    }

    @Test
    @Transactional
    void createLSATurnout() throws Exception {
        int databaseSizeBeforeCreate = lSATurnoutRepository.findAll().size();
        // Create the LSATurnout
        restLSATurnoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lSATurnout)))
            .andExpect(status().isCreated());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeCreate + 1);
        LSATurnout testLSATurnout = lSATurnoutList.get(lSATurnoutList.size() - 1);
        assertThat(testLSATurnout.getStation1()).isEqualTo(DEFAULT_STATION_1);
        assertThat(testLSATurnout.getStation2()).isEqualTo(DEFAULT_STATION_2);
        assertThat(testLSATurnout.getLsaCode()).isEqualTo(DEFAULT_LSA_CODE);
        assertThat(testLSATurnout.getTurnout()).isEqualTo(DEFAULT_TURNOUT);
    }

    @Test
    @Transactional
    void createLSATurnoutWithExistingId() throws Exception {
        // Create the LSATurnout with an existing ID
        lSATurnout.setId(1L);

        int databaseSizeBeforeCreate = lSATurnoutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLSATurnoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lSATurnout)))
            .andExpect(status().isBadRequest());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLSATurnouts() throws Exception {
        // Initialize the database
        lSATurnoutRepository.saveAndFlush(lSATurnout);

        // Get all the lSATurnoutList
        restLSATurnoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lSATurnout.getId().intValue())))
            .andExpect(jsonPath("$.[*].station1").value(hasItem(DEFAULT_STATION_1)))
            .andExpect(jsonPath("$.[*].station2").value(hasItem(DEFAULT_STATION_2)))
            .andExpect(jsonPath("$.[*].lsaCode").value(hasItem(DEFAULT_LSA_CODE)))
            .andExpect(jsonPath("$.[*].turnout").value(hasItem(DEFAULT_TURNOUT)));
    }

    @Test
    @Transactional
    void getLSATurnout() throws Exception {
        // Initialize the database
        lSATurnoutRepository.saveAndFlush(lSATurnout);

        // Get the lSATurnout
        restLSATurnoutMockMvc
            .perform(get(ENTITY_API_URL_ID, lSATurnout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lSATurnout.getId().intValue()))
            .andExpect(jsonPath("$.station1").value(DEFAULT_STATION_1))
            .andExpect(jsonPath("$.station2").value(DEFAULT_STATION_2))
            .andExpect(jsonPath("$.lsaCode").value(DEFAULT_LSA_CODE))
            .andExpect(jsonPath("$.turnout").value(DEFAULT_TURNOUT));
    }

    @Test
    @Transactional
    void getNonExistingLSATurnout() throws Exception {
        // Get the lSATurnout
        restLSATurnoutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLSATurnout() throws Exception {
        // Initialize the database
        lSATurnoutRepository.saveAndFlush(lSATurnout);

        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();

        // Update the lSATurnout
        LSATurnout updatedLSATurnout = lSATurnoutRepository.findById(lSATurnout.getId()).get();
        // Disconnect from session so that the updates on updatedLSATurnout are not directly saved in db
        em.detach(updatedLSATurnout);
        updatedLSATurnout.station1(UPDATED_STATION_1).station2(UPDATED_STATION_2).lsaCode(UPDATED_LSA_CODE).turnout(UPDATED_TURNOUT);

        restLSATurnoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLSATurnout.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLSATurnout))
            )
            .andExpect(status().isOk());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
        LSATurnout testLSATurnout = lSATurnoutList.get(lSATurnoutList.size() - 1);
        assertThat(testLSATurnout.getStation1()).isEqualTo(UPDATED_STATION_1);
        assertThat(testLSATurnout.getStation2()).isEqualTo(UPDATED_STATION_2);
        assertThat(testLSATurnout.getLsaCode()).isEqualTo(UPDATED_LSA_CODE);
        assertThat(testLSATurnout.getTurnout()).isEqualTo(UPDATED_TURNOUT);
    }

    @Test
    @Transactional
    void putNonExistingLSATurnout() throws Exception {
        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();
        lSATurnout.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLSATurnoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lSATurnout.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lSATurnout))
            )
            .andExpect(status().isBadRequest());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLSATurnout() throws Exception {
        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();
        lSATurnout.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLSATurnoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lSATurnout))
            )
            .andExpect(status().isBadRequest());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLSATurnout() throws Exception {
        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();
        lSATurnout.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLSATurnoutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lSATurnout)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLSATurnoutWithPatch() throws Exception {
        // Initialize the database
        lSATurnoutRepository.saveAndFlush(lSATurnout);

        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();

        // Update the lSATurnout using partial update
        LSATurnout partialUpdatedLSATurnout = new LSATurnout();
        partialUpdatedLSATurnout.setId(lSATurnout.getId());

        partialUpdatedLSATurnout.station2(UPDATED_STATION_2).turnout(UPDATED_TURNOUT);

        restLSATurnoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLSATurnout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLSATurnout))
            )
            .andExpect(status().isOk());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
        LSATurnout testLSATurnout = lSATurnoutList.get(lSATurnoutList.size() - 1);
        assertThat(testLSATurnout.getStation1()).isEqualTo(DEFAULT_STATION_1);
        assertThat(testLSATurnout.getStation2()).isEqualTo(UPDATED_STATION_2);
        assertThat(testLSATurnout.getLsaCode()).isEqualTo(DEFAULT_LSA_CODE);
        assertThat(testLSATurnout.getTurnout()).isEqualTo(UPDATED_TURNOUT);
    }

    @Test
    @Transactional
    void fullUpdateLSATurnoutWithPatch() throws Exception {
        // Initialize the database
        lSATurnoutRepository.saveAndFlush(lSATurnout);

        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();

        // Update the lSATurnout using partial update
        LSATurnout partialUpdatedLSATurnout = new LSATurnout();
        partialUpdatedLSATurnout.setId(lSATurnout.getId());

        partialUpdatedLSATurnout.station1(UPDATED_STATION_1).station2(UPDATED_STATION_2).lsaCode(UPDATED_LSA_CODE).turnout(UPDATED_TURNOUT);

        restLSATurnoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLSATurnout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLSATurnout))
            )
            .andExpect(status().isOk());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
        LSATurnout testLSATurnout = lSATurnoutList.get(lSATurnoutList.size() - 1);
        assertThat(testLSATurnout.getStation1()).isEqualTo(UPDATED_STATION_1);
        assertThat(testLSATurnout.getStation2()).isEqualTo(UPDATED_STATION_2);
        assertThat(testLSATurnout.getLsaCode()).isEqualTo(UPDATED_LSA_CODE);
        assertThat(testLSATurnout.getTurnout()).isEqualTo(UPDATED_TURNOUT);
    }

    @Test
    @Transactional
    void patchNonExistingLSATurnout() throws Exception {
        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();
        lSATurnout.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLSATurnoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lSATurnout.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lSATurnout))
            )
            .andExpect(status().isBadRequest());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLSATurnout() throws Exception {
        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();
        lSATurnout.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLSATurnoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lSATurnout))
            )
            .andExpect(status().isBadRequest());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLSATurnout() throws Exception {
        int databaseSizeBeforeUpdate = lSATurnoutRepository.findAll().size();
        lSATurnout.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLSATurnoutMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lSATurnout))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LSATurnout in the database
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLSATurnout() throws Exception {
        // Initialize the database
        lSATurnoutRepository.saveAndFlush(lSATurnout);

        int databaseSizeBeforeDelete = lSATurnoutRepository.findAll().size();

        // Delete the lSATurnout
        restLSATurnoutMockMvc
            .perform(delete(ENTITY_API_URL_ID, lSATurnout.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LSATurnout> lSATurnoutList = lSATurnoutRepository.findAll();
        assertThat(lSATurnoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
