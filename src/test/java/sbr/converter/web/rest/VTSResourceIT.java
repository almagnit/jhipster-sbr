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
import sbr.converter.domain.VTS;
import sbr.converter.repository.VTSRepository;

/**
 * Integration tests for the {@link VTSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VTSResourceIT {

    private static final String DEFAULT_VERKEHRSTAGE = "AAAAAAAAAA";
    private static final String UPDATED_VERKEHRSTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHREIBUNG = "AAAAAAAAAA";
    private static final String UPDATED_BESCHREIBUNG = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIONSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VERSIONSNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_MANDANT = "AAAAAAAAAA";
    private static final String UPDATED_MANDANT = "BBBBBBBBBB";

    private static final String DEFAULT_FEIERTAGE = "AAAAAAAAAA";
    private static final String UPDATED_FEIERTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_GUELTIGKEIT = "AAAAAAAAAA";
    private static final String UPDATED_GUELTIGKEIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VTSRepository vTSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVTSMockMvc;

    private VTS vTS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VTS createEntity(EntityManager em) {
        VTS vTS = new VTS()
            .verkehrstage(DEFAULT_VERKEHRSTAGE)
            .beschreibung(DEFAULT_BESCHREIBUNG)
            .versionsnummer(DEFAULT_VERSIONSNUMMER)
            .mandant(DEFAULT_MANDANT)
            .feiertage(DEFAULT_FEIERTAGE)
            .gueltigkeit(DEFAULT_GUELTIGKEIT);
        return vTS;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VTS createUpdatedEntity(EntityManager em) {
        VTS vTS = new VTS()
            .verkehrstage(UPDATED_VERKEHRSTAGE)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .versionsnummer(UPDATED_VERSIONSNUMMER)
            .mandant(UPDATED_MANDANT)
            .feiertage(UPDATED_FEIERTAGE)
            .gueltigkeit(UPDATED_GUELTIGKEIT);
        return vTS;
    }

    @BeforeEach
    public void initTest() {
        vTS = createEntity(em);
    }

    @Test
    @Transactional
    void createVTS() throws Exception {
        int databaseSizeBeforeCreate = vTSRepository.findAll().size();
        // Create the VTS
        restVTSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vTS)))
            .andExpect(status().isCreated());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeCreate + 1);
        VTS testVTS = vTSList.get(vTSList.size() - 1);
        assertThat(testVTS.getVerkehrstage()).isEqualTo(DEFAULT_VERKEHRSTAGE);
        assertThat(testVTS.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
        assertThat(testVTS.getVersionsnummer()).isEqualTo(DEFAULT_VERSIONSNUMMER);
        assertThat(testVTS.getMandant()).isEqualTo(DEFAULT_MANDANT);
        assertThat(testVTS.getFeiertage()).isEqualTo(DEFAULT_FEIERTAGE);
        assertThat(testVTS.getGueltigkeit()).isEqualTo(DEFAULT_GUELTIGKEIT);
    }

    @Test
    @Transactional
    void createVTSWithExistingId() throws Exception {
        // Create the VTS with an existing ID
        vTS.setId(1L);

        int databaseSizeBeforeCreate = vTSRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVTSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vTS)))
            .andExpect(status().isBadRequest());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVTS() throws Exception {
        // Initialize the database
        vTSRepository.saveAndFlush(vTS);

        // Get all the vTSList
        restVTSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vTS.getId().intValue())))
            .andExpect(jsonPath("$.[*].verkehrstage").value(hasItem(DEFAULT_VERKEHRSTAGE)))
            .andExpect(jsonPath("$.[*].beschreibung").value(hasItem(DEFAULT_BESCHREIBUNG)))
            .andExpect(jsonPath("$.[*].versionsnummer").value(hasItem(DEFAULT_VERSIONSNUMMER)))
            .andExpect(jsonPath("$.[*].mandant").value(hasItem(DEFAULT_MANDANT)))
            .andExpect(jsonPath("$.[*].feiertage").value(hasItem(DEFAULT_FEIERTAGE)))
            .andExpect(jsonPath("$.[*].gueltigkeit").value(hasItem(DEFAULT_GUELTIGKEIT)));
    }

    @Test
    @Transactional
    void getVTS() throws Exception {
        // Initialize the database
        vTSRepository.saveAndFlush(vTS);

        // Get the vTS
        restVTSMockMvc
            .perform(get(ENTITY_API_URL_ID, vTS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vTS.getId().intValue()))
            .andExpect(jsonPath("$.verkehrstage").value(DEFAULT_VERKEHRSTAGE))
            .andExpect(jsonPath("$.beschreibung").value(DEFAULT_BESCHREIBUNG))
            .andExpect(jsonPath("$.versionsnummer").value(DEFAULT_VERSIONSNUMMER))
            .andExpect(jsonPath("$.mandant").value(DEFAULT_MANDANT))
            .andExpect(jsonPath("$.feiertage").value(DEFAULT_FEIERTAGE))
            .andExpect(jsonPath("$.gueltigkeit").value(DEFAULT_GUELTIGKEIT));
    }

    @Test
    @Transactional
    void getNonExistingVTS() throws Exception {
        // Get the vTS
        restVTSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVTS() throws Exception {
        // Initialize the database
        vTSRepository.saveAndFlush(vTS);

        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();

        // Update the vTS
        VTS updatedVTS = vTSRepository.findById(vTS.getId()).get();
        // Disconnect from session so that the updates on updatedVTS are not directly saved in db
        em.detach(updatedVTS);
        updatedVTS
            .verkehrstage(UPDATED_VERKEHRSTAGE)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .versionsnummer(UPDATED_VERSIONSNUMMER)
            .mandant(UPDATED_MANDANT)
            .feiertage(UPDATED_FEIERTAGE)
            .gueltigkeit(UPDATED_GUELTIGKEIT);

        restVTSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVTS.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVTS))
            )
            .andExpect(status().isOk());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
        VTS testVTS = vTSList.get(vTSList.size() - 1);
        assertThat(testVTS.getVerkehrstage()).isEqualTo(UPDATED_VERKEHRSTAGE);
        assertThat(testVTS.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
        assertThat(testVTS.getVersionsnummer()).isEqualTo(UPDATED_VERSIONSNUMMER);
        assertThat(testVTS.getMandant()).isEqualTo(UPDATED_MANDANT);
        assertThat(testVTS.getFeiertage()).isEqualTo(UPDATED_FEIERTAGE);
        assertThat(testVTS.getGueltigkeit()).isEqualTo(UPDATED_GUELTIGKEIT);
    }

    @Test
    @Transactional
    void putNonExistingVTS() throws Exception {
        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();
        vTS.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVTSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vTS.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vTS))
            )
            .andExpect(status().isBadRequest());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVTS() throws Exception {
        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();
        vTS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVTSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vTS))
            )
            .andExpect(status().isBadRequest());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVTS() throws Exception {
        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();
        vTS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVTSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vTS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVTSWithPatch() throws Exception {
        // Initialize the database
        vTSRepository.saveAndFlush(vTS);

        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();

        // Update the vTS using partial update
        VTS partialUpdatedVTS = new VTS();
        partialUpdatedVTS.setId(vTS.getId());

        restVTSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVTS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVTS))
            )
            .andExpect(status().isOk());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
        VTS testVTS = vTSList.get(vTSList.size() - 1);
        assertThat(testVTS.getVerkehrstage()).isEqualTo(DEFAULT_VERKEHRSTAGE);
        assertThat(testVTS.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
        assertThat(testVTS.getVersionsnummer()).isEqualTo(DEFAULT_VERSIONSNUMMER);
        assertThat(testVTS.getMandant()).isEqualTo(DEFAULT_MANDANT);
        assertThat(testVTS.getFeiertage()).isEqualTo(DEFAULT_FEIERTAGE);
        assertThat(testVTS.getGueltigkeit()).isEqualTo(DEFAULT_GUELTIGKEIT);
    }

    @Test
    @Transactional
    void fullUpdateVTSWithPatch() throws Exception {
        // Initialize the database
        vTSRepository.saveAndFlush(vTS);

        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();

        // Update the vTS using partial update
        VTS partialUpdatedVTS = new VTS();
        partialUpdatedVTS.setId(vTS.getId());

        partialUpdatedVTS
            .verkehrstage(UPDATED_VERKEHRSTAGE)
            .beschreibung(UPDATED_BESCHREIBUNG)
            .versionsnummer(UPDATED_VERSIONSNUMMER)
            .mandant(UPDATED_MANDANT)
            .feiertage(UPDATED_FEIERTAGE)
            .gueltigkeit(UPDATED_GUELTIGKEIT);

        restVTSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVTS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVTS))
            )
            .andExpect(status().isOk());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
        VTS testVTS = vTSList.get(vTSList.size() - 1);
        assertThat(testVTS.getVerkehrstage()).isEqualTo(UPDATED_VERKEHRSTAGE);
        assertThat(testVTS.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
        assertThat(testVTS.getVersionsnummer()).isEqualTo(UPDATED_VERSIONSNUMMER);
        assertThat(testVTS.getMandant()).isEqualTo(UPDATED_MANDANT);
        assertThat(testVTS.getFeiertage()).isEqualTo(UPDATED_FEIERTAGE);
        assertThat(testVTS.getGueltigkeit()).isEqualTo(UPDATED_GUELTIGKEIT);
    }

    @Test
    @Transactional
    void patchNonExistingVTS() throws Exception {
        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();
        vTS.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVTSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vTS.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vTS))
            )
            .andExpect(status().isBadRequest());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVTS() throws Exception {
        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();
        vTS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVTSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vTS))
            )
            .andExpect(status().isBadRequest());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVTS() throws Exception {
        int databaseSizeBeforeUpdate = vTSRepository.findAll().size();
        vTS.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVTSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vTS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VTS in the database
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVTS() throws Exception {
        // Initialize the database
        vTSRepository.saveAndFlush(vTS);

        int databaseSizeBeforeDelete = vTSRepository.findAll().size();

        // Delete the vTS
        restVTSMockMvc.perform(delete(ENTITY_API_URL_ID, vTS.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VTS> vTSList = vTSRepository.findAll();
        assertThat(vTSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
