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
import sbr.converter.domain.Fahrten;
import sbr.converter.repository.FahrtenRepository;

/**
 * Integration tests for the {@link FahrtenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FahrtenResourceIT {

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_ZUGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_ZUGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_ZNR_BESCHREIBUNG = "AAAAAAAAAA";
    private static final String UPDATED_ZNR_BESCHREIBUNG = "BBBBBBBBBB";

    private static final String DEFAULT_VARIANTE = "AAAAAAAAAA";
    private static final String UPDATED_VARIANTE = "BBBBBBBBBB";

    private static final String DEFAULT_TAGESART = "AAAAAAAAAA";
    private static final String UPDATED_TAGESART = "BBBBBBBBBB";

    private static final String DEFAULT_UMLAUF = "AAAAAAAAAA";
    private static final String UPDATED_UMLAUF = "BBBBBBBBBB";

    private static final String DEFAULT_UMLAUFINDEX = "AAAAAAAAAA";
    private static final String UPDATED_UMLAUFINDEX = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fahrtens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FahrtenRepository fahrtenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFahrtenMockMvc;

    private Fahrten fahrten;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fahrten createEntity(EntityManager em) {
        Fahrten fahrten = new Fahrten()
            .titel(DEFAULT_TITEL)
            .zugnummer(DEFAULT_ZUGNUMMER)
            .znrBeschreibung(DEFAULT_ZNR_BESCHREIBUNG)
            .variante(DEFAULT_VARIANTE)
            .tagesart(DEFAULT_TAGESART)
            .umlauf(DEFAULT_UMLAUF)
            .umlaufindex(DEFAULT_UMLAUFINDEX);
        return fahrten;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fahrten createUpdatedEntity(EntityManager em) {
        Fahrten fahrten = new Fahrten()
            .titel(UPDATED_TITEL)
            .zugnummer(UPDATED_ZUGNUMMER)
            .znrBeschreibung(UPDATED_ZNR_BESCHREIBUNG)
            .variante(UPDATED_VARIANTE)
            .tagesart(UPDATED_TAGESART)
            .umlauf(UPDATED_UMLAUF)
            .umlaufindex(UPDATED_UMLAUFINDEX);
        return fahrten;
    }

    @BeforeEach
    public void initTest() {
        fahrten = createEntity(em);
    }

    @Test
    @Transactional
    void createFahrten() throws Exception {
        int databaseSizeBeforeCreate = fahrtenRepository.findAll().size();
        // Create the Fahrten
        restFahrtenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fahrten)))
            .andExpect(status().isCreated());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeCreate + 1);
        Fahrten testFahrten = fahrtenList.get(fahrtenList.size() - 1);
        assertThat(testFahrten.getTitel()).isEqualTo(DEFAULT_TITEL);
        assertThat(testFahrten.getZugnummer()).isEqualTo(DEFAULT_ZUGNUMMER);
        assertThat(testFahrten.getZnrBeschreibung()).isEqualTo(DEFAULT_ZNR_BESCHREIBUNG);
        assertThat(testFahrten.getVariante()).isEqualTo(DEFAULT_VARIANTE);
        assertThat(testFahrten.getTagesart()).isEqualTo(DEFAULT_TAGESART);
        assertThat(testFahrten.getUmlauf()).isEqualTo(DEFAULT_UMLAUF);
        assertThat(testFahrten.getUmlaufindex()).isEqualTo(DEFAULT_UMLAUFINDEX);
    }

    @Test
    @Transactional
    void createFahrtenWithExistingId() throws Exception {
        // Create the Fahrten with an existing ID
        fahrten.setId(1L);

        int databaseSizeBeforeCreate = fahrtenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFahrtenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fahrten)))
            .andExpect(status().isBadRequest());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFahrtens() throws Exception {
        // Initialize the database
        fahrtenRepository.saveAndFlush(fahrten);

        // Get all the fahrtenList
        restFahrtenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fahrten.getId().intValue())))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)))
            .andExpect(jsonPath("$.[*].zugnummer").value(hasItem(DEFAULT_ZUGNUMMER)))
            .andExpect(jsonPath("$.[*].znrBeschreibung").value(hasItem(DEFAULT_ZNR_BESCHREIBUNG)))
            .andExpect(jsonPath("$.[*].variante").value(hasItem(DEFAULT_VARIANTE)))
            .andExpect(jsonPath("$.[*].tagesart").value(hasItem(DEFAULT_TAGESART)))
            .andExpect(jsonPath("$.[*].umlauf").value(hasItem(DEFAULT_UMLAUF)))
            .andExpect(jsonPath("$.[*].umlaufindex").value(hasItem(DEFAULT_UMLAUFINDEX)));
    }

    @Test
    @Transactional
    void getFahrten() throws Exception {
        // Initialize the database
        fahrtenRepository.saveAndFlush(fahrten);

        // Get the fahrten
        restFahrtenMockMvc
            .perform(get(ENTITY_API_URL_ID, fahrten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fahrten.getId().intValue()))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL))
            .andExpect(jsonPath("$.zugnummer").value(DEFAULT_ZUGNUMMER))
            .andExpect(jsonPath("$.znrBeschreibung").value(DEFAULT_ZNR_BESCHREIBUNG))
            .andExpect(jsonPath("$.variante").value(DEFAULT_VARIANTE))
            .andExpect(jsonPath("$.tagesart").value(DEFAULT_TAGESART))
            .andExpect(jsonPath("$.umlauf").value(DEFAULT_UMLAUF))
            .andExpect(jsonPath("$.umlaufindex").value(DEFAULT_UMLAUFINDEX));
    }

    @Test
    @Transactional
    void getNonExistingFahrten() throws Exception {
        // Get the fahrten
        restFahrtenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFahrten() throws Exception {
        // Initialize the database
        fahrtenRepository.saveAndFlush(fahrten);

        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();

        // Update the fahrten
        Fahrten updatedFahrten = fahrtenRepository.findById(fahrten.getId()).get();
        // Disconnect from session so that the updates on updatedFahrten are not directly saved in db
        em.detach(updatedFahrten);
        updatedFahrten
            .titel(UPDATED_TITEL)
            .zugnummer(UPDATED_ZUGNUMMER)
            .znrBeschreibung(UPDATED_ZNR_BESCHREIBUNG)
            .variante(UPDATED_VARIANTE)
            .tagesart(UPDATED_TAGESART)
            .umlauf(UPDATED_UMLAUF)
            .umlaufindex(UPDATED_UMLAUFINDEX);

        restFahrtenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFahrten.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFahrten))
            )
            .andExpect(status().isOk());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
        Fahrten testFahrten = fahrtenList.get(fahrtenList.size() - 1);
        assertThat(testFahrten.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testFahrten.getZugnummer()).isEqualTo(UPDATED_ZUGNUMMER);
        assertThat(testFahrten.getZnrBeschreibung()).isEqualTo(UPDATED_ZNR_BESCHREIBUNG);
        assertThat(testFahrten.getVariante()).isEqualTo(UPDATED_VARIANTE);
        assertThat(testFahrten.getTagesart()).isEqualTo(UPDATED_TAGESART);
        assertThat(testFahrten.getUmlauf()).isEqualTo(UPDATED_UMLAUF);
        assertThat(testFahrten.getUmlaufindex()).isEqualTo(UPDATED_UMLAUFINDEX);
    }

    @Test
    @Transactional
    void putNonExistingFahrten() throws Exception {
        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();
        fahrten.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFahrtenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fahrten.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fahrten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFahrten() throws Exception {
        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();
        fahrten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFahrtenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fahrten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFahrten() throws Exception {
        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();
        fahrten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFahrtenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fahrten)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFahrtenWithPatch() throws Exception {
        // Initialize the database
        fahrtenRepository.saveAndFlush(fahrten);

        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();

        // Update the fahrten using partial update
        Fahrten partialUpdatedFahrten = new Fahrten();
        partialUpdatedFahrten.setId(fahrten.getId());

        partialUpdatedFahrten
            .zugnummer(UPDATED_ZUGNUMMER)
            .znrBeschreibung(UPDATED_ZNR_BESCHREIBUNG)
            .tagesart(UPDATED_TAGESART)
            .umlaufindex(UPDATED_UMLAUFINDEX);

        restFahrtenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFahrten.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFahrten))
            )
            .andExpect(status().isOk());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
        Fahrten testFahrten = fahrtenList.get(fahrtenList.size() - 1);
        assertThat(testFahrten.getTitel()).isEqualTo(DEFAULT_TITEL);
        assertThat(testFahrten.getZugnummer()).isEqualTo(UPDATED_ZUGNUMMER);
        assertThat(testFahrten.getZnrBeschreibung()).isEqualTo(UPDATED_ZNR_BESCHREIBUNG);
        assertThat(testFahrten.getVariante()).isEqualTo(DEFAULT_VARIANTE);
        assertThat(testFahrten.getTagesart()).isEqualTo(UPDATED_TAGESART);
        assertThat(testFahrten.getUmlauf()).isEqualTo(DEFAULT_UMLAUF);
        assertThat(testFahrten.getUmlaufindex()).isEqualTo(UPDATED_UMLAUFINDEX);
    }

    @Test
    @Transactional
    void fullUpdateFahrtenWithPatch() throws Exception {
        // Initialize the database
        fahrtenRepository.saveAndFlush(fahrten);

        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();

        // Update the fahrten using partial update
        Fahrten partialUpdatedFahrten = new Fahrten();
        partialUpdatedFahrten.setId(fahrten.getId());

        partialUpdatedFahrten
            .titel(UPDATED_TITEL)
            .zugnummer(UPDATED_ZUGNUMMER)
            .znrBeschreibung(UPDATED_ZNR_BESCHREIBUNG)
            .variante(UPDATED_VARIANTE)
            .tagesart(UPDATED_TAGESART)
            .umlauf(UPDATED_UMLAUF)
            .umlaufindex(UPDATED_UMLAUFINDEX);

        restFahrtenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFahrten.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFahrten))
            )
            .andExpect(status().isOk());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
        Fahrten testFahrten = fahrtenList.get(fahrtenList.size() - 1);
        assertThat(testFahrten.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testFahrten.getZugnummer()).isEqualTo(UPDATED_ZUGNUMMER);
        assertThat(testFahrten.getZnrBeschreibung()).isEqualTo(UPDATED_ZNR_BESCHREIBUNG);
        assertThat(testFahrten.getVariante()).isEqualTo(UPDATED_VARIANTE);
        assertThat(testFahrten.getTagesart()).isEqualTo(UPDATED_TAGESART);
        assertThat(testFahrten.getUmlauf()).isEqualTo(UPDATED_UMLAUF);
        assertThat(testFahrten.getUmlaufindex()).isEqualTo(UPDATED_UMLAUFINDEX);
    }

    @Test
    @Transactional
    void patchNonExistingFahrten() throws Exception {
        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();
        fahrten.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFahrtenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fahrten.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fahrten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFahrten() throws Exception {
        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();
        fahrten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFahrtenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fahrten))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFahrten() throws Exception {
        int databaseSizeBeforeUpdate = fahrtenRepository.findAll().size();
        fahrten.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFahrtenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fahrten)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fahrten in the database
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFahrten() throws Exception {
        // Initialize the database
        fahrtenRepository.saveAndFlush(fahrten);

        int databaseSizeBeforeDelete = fahrtenRepository.findAll().size();

        // Delete the fahrten
        restFahrtenMockMvc
            .perform(delete(ENTITY_API_URL_ID, fahrten.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fahrten> fahrtenList = fahrtenRepository.findAll();
        assertThat(fahrtenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
