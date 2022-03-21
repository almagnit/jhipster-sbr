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
import sbr.converter.domain.SpecialInfo;
import sbr.converter.repository.SpecialInfoRepository;

/**
 * Integration tests for the {@link SpecialInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecialInfoResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_TERMINAL_DESC = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_TERMINAL_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_TERMINAL_DESC = "AAAAAAAAAA";
    private static final String UPDATED_LONG_TERMINAL_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DS_003 = "AAAAAAAAAA";
    private static final String UPDATED_DS_003 = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/special-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpecialInfoRepository specialInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialInfoMockMvc;

    private SpecialInfo specialInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpecialInfo createEntity(EntityManager em) {
        SpecialInfo specialInfo = new SpecialInfo()
            .code(DEFAULT_CODE)
            .item(DEFAULT_ITEM)
            .attribute(DEFAULT_ATTRIBUTE)
            .shortTerminalDesc(DEFAULT_SHORT_TERMINAL_DESC)
            .longTerminalDesc(DEFAULT_LONG_TERMINAL_DESC)
            .displayText(DEFAULT_DISPLAY_TEXT)
            .ds003(DEFAULT_DS_003)
            .language(DEFAULT_LANGUAGE);
        return specialInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpecialInfo createUpdatedEntity(EntityManager em) {
        SpecialInfo specialInfo = new SpecialInfo()
            .code(UPDATED_CODE)
            .item(UPDATED_ITEM)
            .attribute(UPDATED_ATTRIBUTE)
            .shortTerminalDesc(UPDATED_SHORT_TERMINAL_DESC)
            .longTerminalDesc(UPDATED_LONG_TERMINAL_DESC)
            .displayText(UPDATED_DISPLAY_TEXT)
            .ds003(UPDATED_DS_003)
            .language(UPDATED_LANGUAGE);
        return specialInfo;
    }

    @BeforeEach
    public void initTest() {
        specialInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecialInfo() throws Exception {
        int databaseSizeBeforeCreate = specialInfoRepository.findAll().size();
        // Create the SpecialInfo
        restSpecialInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialInfo)))
            .andExpect(status().isCreated());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialInfo testSpecialInfo = specialInfoList.get(specialInfoList.size() - 1);
        assertThat(testSpecialInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSpecialInfo.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testSpecialInfo.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
        assertThat(testSpecialInfo.getShortTerminalDesc()).isEqualTo(DEFAULT_SHORT_TERMINAL_DESC);
        assertThat(testSpecialInfo.getLongTerminalDesc()).isEqualTo(DEFAULT_LONG_TERMINAL_DESC);
        assertThat(testSpecialInfo.getDisplayText()).isEqualTo(DEFAULT_DISPLAY_TEXT);
        assertThat(testSpecialInfo.getDs003()).isEqualTo(DEFAULT_DS_003);
        assertThat(testSpecialInfo.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createSpecialInfoWithExistingId() throws Exception {
        // Create the SpecialInfo with an existing ID
        specialInfo.setId(1L);

        int databaseSizeBeforeCreate = specialInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialInfo)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpecialInfos() throws Exception {
        // Initialize the database
        specialInfoRepository.saveAndFlush(specialInfo);

        // Get all the specialInfoList
        restSpecialInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE)))
            .andExpect(jsonPath("$.[*].shortTerminalDesc").value(hasItem(DEFAULT_SHORT_TERMINAL_DESC)))
            .andExpect(jsonPath("$.[*].longTerminalDesc").value(hasItem(DEFAULT_LONG_TERMINAL_DESC)))
            .andExpect(jsonPath("$.[*].displayText").value(hasItem(DEFAULT_DISPLAY_TEXT)))
            .andExpect(jsonPath("$.[*].ds003").value(hasItem(DEFAULT_DS_003)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getSpecialInfo() throws Exception {
        // Initialize the database
        specialInfoRepository.saveAndFlush(specialInfo);

        // Get the specialInfo
        restSpecialInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, specialInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialInfo.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE))
            .andExpect(jsonPath("$.shortTerminalDesc").value(DEFAULT_SHORT_TERMINAL_DESC))
            .andExpect(jsonPath("$.longTerminalDesc").value(DEFAULT_LONG_TERMINAL_DESC))
            .andExpect(jsonPath("$.displayText").value(DEFAULT_DISPLAY_TEXT))
            .andExpect(jsonPath("$.ds003").value(DEFAULT_DS_003))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getNonExistingSpecialInfo() throws Exception {
        // Get the specialInfo
        restSpecialInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSpecialInfo() throws Exception {
        // Initialize the database
        specialInfoRepository.saveAndFlush(specialInfo);

        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();

        // Update the specialInfo
        SpecialInfo updatedSpecialInfo = specialInfoRepository.findById(specialInfo.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialInfo are not directly saved in db
        em.detach(updatedSpecialInfo);
        updatedSpecialInfo
            .code(UPDATED_CODE)
            .item(UPDATED_ITEM)
            .attribute(UPDATED_ATTRIBUTE)
            .shortTerminalDesc(UPDATED_SHORT_TERMINAL_DESC)
            .longTerminalDesc(UPDATED_LONG_TERMINAL_DESC)
            .displayText(UPDATED_DISPLAY_TEXT)
            .ds003(UPDATED_DS_003)
            .language(UPDATED_LANGUAGE);

        restSpecialInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpecialInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpecialInfo))
            )
            .andExpect(status().isOk());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
        SpecialInfo testSpecialInfo = specialInfoList.get(specialInfoList.size() - 1);
        assertThat(testSpecialInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSpecialInfo.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testSpecialInfo.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
        assertThat(testSpecialInfo.getShortTerminalDesc()).isEqualTo(UPDATED_SHORT_TERMINAL_DESC);
        assertThat(testSpecialInfo.getLongTerminalDesc()).isEqualTo(UPDATED_LONG_TERMINAL_DESC);
        assertThat(testSpecialInfo.getDisplayText()).isEqualTo(UPDATED_DISPLAY_TEXT);
        assertThat(testSpecialInfo.getDs003()).isEqualTo(UPDATED_DS_003);
        assertThat(testSpecialInfo.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingSpecialInfo() throws Exception {
        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();
        specialInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecialInfo() throws Exception {
        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();
        specialInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecialInfo() throws Exception {
        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();
        specialInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecialInfoWithPatch() throws Exception {
        // Initialize the database
        specialInfoRepository.saveAndFlush(specialInfo);

        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();

        // Update the specialInfo using partial update
        SpecialInfo partialUpdatedSpecialInfo = new SpecialInfo();
        partialUpdatedSpecialInfo.setId(specialInfo.getId());

        partialUpdatedSpecialInfo
            .attribute(UPDATED_ATTRIBUTE)
            .shortTerminalDesc(UPDATED_SHORT_TERMINAL_DESC)
            .longTerminalDesc(UPDATED_LONG_TERMINAL_DESC)
            .displayText(UPDATED_DISPLAY_TEXT)
            .ds003(UPDATED_DS_003)
            .language(UPDATED_LANGUAGE);

        restSpecialInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialInfo))
            )
            .andExpect(status().isOk());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
        SpecialInfo testSpecialInfo = specialInfoList.get(specialInfoList.size() - 1);
        assertThat(testSpecialInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSpecialInfo.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testSpecialInfo.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
        assertThat(testSpecialInfo.getShortTerminalDesc()).isEqualTo(UPDATED_SHORT_TERMINAL_DESC);
        assertThat(testSpecialInfo.getLongTerminalDesc()).isEqualTo(UPDATED_LONG_TERMINAL_DESC);
        assertThat(testSpecialInfo.getDisplayText()).isEqualTo(UPDATED_DISPLAY_TEXT);
        assertThat(testSpecialInfo.getDs003()).isEqualTo(UPDATED_DS_003);
        assertThat(testSpecialInfo.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateSpecialInfoWithPatch() throws Exception {
        // Initialize the database
        specialInfoRepository.saveAndFlush(specialInfo);

        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();

        // Update the specialInfo using partial update
        SpecialInfo partialUpdatedSpecialInfo = new SpecialInfo();
        partialUpdatedSpecialInfo.setId(specialInfo.getId());

        partialUpdatedSpecialInfo
            .code(UPDATED_CODE)
            .item(UPDATED_ITEM)
            .attribute(UPDATED_ATTRIBUTE)
            .shortTerminalDesc(UPDATED_SHORT_TERMINAL_DESC)
            .longTerminalDesc(UPDATED_LONG_TERMINAL_DESC)
            .displayText(UPDATED_DISPLAY_TEXT)
            .ds003(UPDATED_DS_003)
            .language(UPDATED_LANGUAGE);

        restSpecialInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialInfo))
            )
            .andExpect(status().isOk());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
        SpecialInfo testSpecialInfo = specialInfoList.get(specialInfoList.size() - 1);
        assertThat(testSpecialInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSpecialInfo.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testSpecialInfo.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
        assertThat(testSpecialInfo.getShortTerminalDesc()).isEqualTo(UPDATED_SHORT_TERMINAL_DESC);
        assertThat(testSpecialInfo.getLongTerminalDesc()).isEqualTo(UPDATED_LONG_TERMINAL_DESC);
        assertThat(testSpecialInfo.getDisplayText()).isEqualTo(UPDATED_DISPLAY_TEXT);
        assertThat(testSpecialInfo.getDs003()).isEqualTo(UPDATED_DS_003);
        assertThat(testSpecialInfo.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingSpecialInfo() throws Exception {
        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();
        specialInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specialInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecialInfo() throws Exception {
        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();
        specialInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecialInfo() throws Exception {
        int databaseSizeBeforeUpdate = specialInfoRepository.findAll().size();
        specialInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(specialInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpecialInfo in the database
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecialInfo() throws Exception {
        // Initialize the database
        specialInfoRepository.saveAndFlush(specialInfo);

        int databaseSizeBeforeDelete = specialInfoRepository.findAll().size();

        // Delete the specialInfo
        restSpecialInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, specialInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpecialInfo> specialInfoList = specialInfoRepository.findAll();
        assertThat(specialInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
