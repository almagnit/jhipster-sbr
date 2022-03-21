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
import sbr.converter.domain.FunctionText;
import sbr.converter.repository.FunctionTextRepository;

/**
 * Integration tests for the {@link FunctionTextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FunctionTextResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/function-texts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FunctionTextRepository functionTextRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctionTextMockMvc;

    private FunctionText functionText;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FunctionText createEntity(EntityManager em) {
        FunctionText functionText = new FunctionText().code(DEFAULT_CODE).text(DEFAULT_TEXT).language(DEFAULT_LANGUAGE);
        return functionText;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FunctionText createUpdatedEntity(EntityManager em) {
        FunctionText functionText = new FunctionText().code(UPDATED_CODE).text(UPDATED_TEXT).language(UPDATED_LANGUAGE);
        return functionText;
    }

    @BeforeEach
    public void initTest() {
        functionText = createEntity(em);
    }

    @Test
    @Transactional
    void createFunctionText() throws Exception {
        int databaseSizeBeforeCreate = functionTextRepository.findAll().size();
        // Create the FunctionText
        restFunctionTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionText)))
            .andExpect(status().isCreated());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeCreate + 1);
        FunctionText testFunctionText = functionTextList.get(functionTextList.size() - 1);
        assertThat(testFunctionText.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFunctionText.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testFunctionText.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createFunctionTextWithExistingId() throws Exception {
        // Create the FunctionText with an existing ID
        functionText.setId(1L);

        int databaseSizeBeforeCreate = functionTextRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctionTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionText)))
            .andExpect(status().isBadRequest());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFunctionTexts() throws Exception {
        // Initialize the database
        functionTextRepository.saveAndFlush(functionText);

        // Get all the functionTextList
        restFunctionTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functionText.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getFunctionText() throws Exception {
        // Initialize the database
        functionTextRepository.saveAndFlush(functionText);

        // Get the functionText
        restFunctionTextMockMvc
            .perform(get(ENTITY_API_URL_ID, functionText.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functionText.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getNonExistingFunctionText() throws Exception {
        // Get the functionText
        restFunctionTextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFunctionText() throws Exception {
        // Initialize the database
        functionTextRepository.saveAndFlush(functionText);

        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();

        // Update the functionText
        FunctionText updatedFunctionText = functionTextRepository.findById(functionText.getId()).get();
        // Disconnect from session so that the updates on updatedFunctionText are not directly saved in db
        em.detach(updatedFunctionText);
        updatedFunctionText.code(UPDATED_CODE).text(UPDATED_TEXT).language(UPDATED_LANGUAGE);

        restFunctionTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFunctionText.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFunctionText))
            )
            .andExpect(status().isOk());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
        FunctionText testFunctionText = functionTextList.get(functionTextList.size() - 1);
        assertThat(testFunctionText.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFunctionText.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testFunctionText.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingFunctionText() throws Exception {
        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();
        functionText.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, functionText.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionText))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunctionText() throws Exception {
        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();
        functionText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionText))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunctionText() throws Exception {
        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();
        functionText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionTextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionText)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFunctionTextWithPatch() throws Exception {
        // Initialize the database
        functionTextRepository.saveAndFlush(functionText);

        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();

        // Update the functionText using partial update
        FunctionText partialUpdatedFunctionText = new FunctionText();
        partialUpdatedFunctionText.setId(functionText.getId());

        partialUpdatedFunctionText.text(UPDATED_TEXT);

        restFunctionTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctionText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctionText))
            )
            .andExpect(status().isOk());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
        FunctionText testFunctionText = functionTextList.get(functionTextList.size() - 1);
        assertThat(testFunctionText.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFunctionText.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testFunctionText.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateFunctionTextWithPatch() throws Exception {
        // Initialize the database
        functionTextRepository.saveAndFlush(functionText);

        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();

        // Update the functionText using partial update
        FunctionText partialUpdatedFunctionText = new FunctionText();
        partialUpdatedFunctionText.setId(functionText.getId());

        partialUpdatedFunctionText.code(UPDATED_CODE).text(UPDATED_TEXT).language(UPDATED_LANGUAGE);

        restFunctionTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctionText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctionText))
            )
            .andExpect(status().isOk());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
        FunctionText testFunctionText = functionTextList.get(functionTextList.size() - 1);
        assertThat(testFunctionText.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFunctionText.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testFunctionText.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingFunctionText() throws Exception {
        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();
        functionText.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, functionText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionText))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunctionText() throws Exception {
        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();
        functionText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionText))
            )
            .andExpect(status().isBadRequest());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunctionText() throws Exception {
        int databaseSizeBeforeUpdate = functionTextRepository.findAll().size();
        functionText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionTextMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(functionText))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FunctionText in the database
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunctionText() throws Exception {
        // Initialize the database
        functionTextRepository.saveAndFlush(functionText);

        int databaseSizeBeforeDelete = functionTextRepository.findAll().size();

        // Delete the functionText
        restFunctionTextMockMvc
            .perform(delete(ENTITY_API_URL_ID, functionText.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FunctionText> functionTextList = functionTextRepository.findAll();
        assertThat(functionTextList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
