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
import sbr.converter.domain.Ziel;
import sbr.converter.repository.ZielRepository;

/**
 * Integration tests for the {@link ZielResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZielResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ziels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ZielRepository zielRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZielMockMvc;

    private Ziel ziel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ziel createEntity(EntityManager em) {
        Ziel ziel = new Ziel()
            .code(DEFAULT_CODE)
            .front(DEFAULT_FRONT)
            .seite1(DEFAULT_SEITE_1)
            .seite2(DEFAULT_SEITE_2)
            .innen(DEFAULT_INNEN)
            .tft(DEFAULT_TFT)
            .terminal(DEFAULT_TERMINAL)
            .language(DEFAULT_LANGUAGE);
        return ziel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ziel createUpdatedEntity(EntityManager em) {
        Ziel ziel = new Ziel()
            .code(UPDATED_CODE)
            .front(UPDATED_FRONT)
            .seite1(UPDATED_SEITE_1)
            .seite2(UPDATED_SEITE_2)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL)
            .language(UPDATED_LANGUAGE);
        return ziel;
    }

    @BeforeEach
    public void initTest() {
        ziel = createEntity(em);
    }

    @Test
    @Transactional
    void createZiel() throws Exception {
        int databaseSizeBeforeCreate = zielRepository.findAll().size();
        // Create the Ziel
        restZielMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ziel)))
            .andExpect(status().isCreated());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeCreate + 1);
        Ziel testZiel = zielList.get(zielList.size() - 1);
        assertThat(testZiel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testZiel.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testZiel.getSeite1()).isEqualTo(DEFAULT_SEITE_1);
        assertThat(testZiel.getSeite2()).isEqualTo(DEFAULT_SEITE_2);
        assertThat(testZiel.getInnen()).isEqualTo(DEFAULT_INNEN);
        assertThat(testZiel.getTft()).isEqualTo(DEFAULT_TFT);
        assertThat(testZiel.getTerminal()).isEqualTo(DEFAULT_TERMINAL);
        assertThat(testZiel.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createZielWithExistingId() throws Exception {
        // Create the Ziel with an existing ID
        ziel.setId(1L);

        int databaseSizeBeforeCreate = zielRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZielMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ziel)))
            .andExpect(status().isBadRequest());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZiels() throws Exception {
        // Initialize the database
        zielRepository.saveAndFlush(ziel);

        // Get all the zielList
        restZielMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ziel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].front").value(hasItem(DEFAULT_FRONT)))
            .andExpect(jsonPath("$.[*].seite1").value(hasItem(DEFAULT_SEITE_1)))
            .andExpect(jsonPath("$.[*].seite2").value(hasItem(DEFAULT_SEITE_2)))
            .andExpect(jsonPath("$.[*].innen").value(hasItem(DEFAULT_INNEN)))
            .andExpect(jsonPath("$.[*].tft").value(hasItem(DEFAULT_TFT)))
            .andExpect(jsonPath("$.[*].terminal").value(hasItem(DEFAULT_TERMINAL)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getZiel() throws Exception {
        // Initialize the database
        zielRepository.saveAndFlush(ziel);

        // Get the ziel
        restZielMockMvc
            .perform(get(ENTITY_API_URL_ID, ziel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ziel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.front").value(DEFAULT_FRONT))
            .andExpect(jsonPath("$.seite1").value(DEFAULT_SEITE_1))
            .andExpect(jsonPath("$.seite2").value(DEFAULT_SEITE_2))
            .andExpect(jsonPath("$.innen").value(DEFAULT_INNEN))
            .andExpect(jsonPath("$.tft").value(DEFAULT_TFT))
            .andExpect(jsonPath("$.terminal").value(DEFAULT_TERMINAL))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getNonExistingZiel() throws Exception {
        // Get the ziel
        restZielMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewZiel() throws Exception {
        // Initialize the database
        zielRepository.saveAndFlush(ziel);

        int databaseSizeBeforeUpdate = zielRepository.findAll().size();

        // Update the ziel
        Ziel updatedZiel = zielRepository.findById(ziel.getId()).get();
        // Disconnect from session so that the updates on updatedZiel are not directly saved in db
        em.detach(updatedZiel);
        updatedZiel
            .code(UPDATED_CODE)
            .front(UPDATED_FRONT)
            .seite1(UPDATED_SEITE_1)
            .seite2(UPDATED_SEITE_2)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL)
            .language(UPDATED_LANGUAGE);

        restZielMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZiel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedZiel))
            )
            .andExpect(status().isOk());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
        Ziel testZiel = zielList.get(zielList.size() - 1);
        assertThat(testZiel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testZiel.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testZiel.getSeite1()).isEqualTo(UPDATED_SEITE_1);
        assertThat(testZiel.getSeite2()).isEqualTo(UPDATED_SEITE_2);
        assertThat(testZiel.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testZiel.getTft()).isEqualTo(UPDATED_TFT);
        assertThat(testZiel.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testZiel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingZiel() throws Exception {
        int databaseSizeBeforeUpdate = zielRepository.findAll().size();
        ziel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZielMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ziel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ziel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZiel() throws Exception {
        int databaseSizeBeforeUpdate = zielRepository.findAll().size();
        ziel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZielMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ziel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZiel() throws Exception {
        int databaseSizeBeforeUpdate = zielRepository.findAll().size();
        ziel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZielMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ziel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZielWithPatch() throws Exception {
        // Initialize the database
        zielRepository.saveAndFlush(ziel);

        int databaseSizeBeforeUpdate = zielRepository.findAll().size();

        // Update the ziel using partial update
        Ziel partialUpdatedZiel = new Ziel();
        partialUpdatedZiel.setId(ziel.getId());

        partialUpdatedZiel.seite1(UPDATED_SEITE_1).terminal(UPDATED_TERMINAL);

        restZielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZiel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZiel))
            )
            .andExpect(status().isOk());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
        Ziel testZiel = zielList.get(zielList.size() - 1);
        assertThat(testZiel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testZiel.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testZiel.getSeite1()).isEqualTo(UPDATED_SEITE_1);
        assertThat(testZiel.getSeite2()).isEqualTo(DEFAULT_SEITE_2);
        assertThat(testZiel.getInnen()).isEqualTo(DEFAULT_INNEN);
        assertThat(testZiel.getTft()).isEqualTo(DEFAULT_TFT);
        assertThat(testZiel.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testZiel.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateZielWithPatch() throws Exception {
        // Initialize the database
        zielRepository.saveAndFlush(ziel);

        int databaseSizeBeforeUpdate = zielRepository.findAll().size();

        // Update the ziel using partial update
        Ziel partialUpdatedZiel = new Ziel();
        partialUpdatedZiel.setId(ziel.getId());

        partialUpdatedZiel
            .code(UPDATED_CODE)
            .front(UPDATED_FRONT)
            .seite1(UPDATED_SEITE_1)
            .seite2(UPDATED_SEITE_2)
            .innen(UPDATED_INNEN)
            .tft(UPDATED_TFT)
            .terminal(UPDATED_TERMINAL)
            .language(UPDATED_LANGUAGE);

        restZielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZiel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZiel))
            )
            .andExpect(status().isOk());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
        Ziel testZiel = zielList.get(zielList.size() - 1);
        assertThat(testZiel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testZiel.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testZiel.getSeite1()).isEqualTo(UPDATED_SEITE_1);
        assertThat(testZiel.getSeite2()).isEqualTo(UPDATED_SEITE_2);
        assertThat(testZiel.getInnen()).isEqualTo(UPDATED_INNEN);
        assertThat(testZiel.getTft()).isEqualTo(UPDATED_TFT);
        assertThat(testZiel.getTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testZiel.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingZiel() throws Exception {
        int databaseSizeBeforeUpdate = zielRepository.findAll().size();
        ziel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ziel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ziel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZiel() throws Exception {
        int databaseSizeBeforeUpdate = zielRepository.findAll().size();
        ziel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZielMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ziel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZiel() throws Exception {
        int databaseSizeBeforeUpdate = zielRepository.findAll().size();
        ziel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZielMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ziel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ziel in the database
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZiel() throws Exception {
        // Initialize the database
        zielRepository.saveAndFlush(ziel);

        int databaseSizeBeforeDelete = zielRepository.findAll().size();

        // Delete the ziel
        restZielMockMvc
            .perform(delete(ENTITY_API_URL_ID, ziel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ziel> zielList = zielRepository.findAll();
        assertThat(zielList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
