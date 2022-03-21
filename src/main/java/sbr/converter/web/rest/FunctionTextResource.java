package sbr.converter.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sbr.converter.domain.FunctionText;
import sbr.converter.repository.FunctionTextRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.FunctionText}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FunctionTextResource {

    private final Logger log = LoggerFactory.getLogger(FunctionTextResource.class);

    private static final String ENTITY_NAME = "functionText";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctionTextRepository functionTextRepository;

    public FunctionTextResource(FunctionTextRepository functionTextRepository) {
        this.functionTextRepository = functionTextRepository;
    }

    /**
     * {@code POST  /function-texts} : Create a new functionText.
     *
     * @param functionText the functionText to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functionText, or with status {@code 400 (Bad Request)} if the functionText has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/function-texts")
    public ResponseEntity<FunctionText> createFunctionText(@RequestBody FunctionText functionText) throws URISyntaxException {
        log.debug("REST request to save FunctionText : {}", functionText);
        if (functionText.getId() != null) {
            throw new BadRequestAlertException("A new functionText cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FunctionText result = functionTextRepository.save(functionText);
        return ResponseEntity
            .created(new URI("/api/function-texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /function-texts/:id} : Updates an existing functionText.
     *
     * @param id the id of the functionText to save.
     * @param functionText the functionText to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionText,
     * or with status {@code 400 (Bad Request)} if the functionText is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functionText couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/function-texts/{id}")
    public ResponseEntity<FunctionText> updateFunctionText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FunctionText functionText
    ) throws URISyntaxException {
        log.debug("REST request to update FunctionText : {}, {}", id, functionText);
        if (functionText.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionText.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FunctionText result = functionTextRepository.save(functionText);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionText.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /function-texts/:id} : Partial updates given fields of an existing functionText, field will ignore if it is null
     *
     * @param id the id of the functionText to save.
     * @param functionText the functionText to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionText,
     * or with status {@code 400 (Bad Request)} if the functionText is not valid,
     * or with status {@code 404 (Not Found)} if the functionText is not found,
     * or with status {@code 500 (Internal Server Error)} if the functionText couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/function-texts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FunctionText> partialUpdateFunctionText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FunctionText functionText
    ) throws URISyntaxException {
        log.debug("REST request to partial update FunctionText partially : {}, {}", id, functionText);
        if (functionText.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionText.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FunctionText> result = functionTextRepository
            .findById(functionText.getId())
            .map(existingFunctionText -> {
                if (functionText.getCode() != null) {
                    existingFunctionText.setCode(functionText.getCode());
                }
                if (functionText.getText() != null) {
                    existingFunctionText.setText(functionText.getText());
                }
                if (functionText.getLanguage() != null) {
                    existingFunctionText.setLanguage(functionText.getLanguage());
                }

                return existingFunctionText;
            })
            .map(functionTextRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionText.getId().toString())
        );
    }

    /**
     * {@code GET  /function-texts} : get all the functionTexts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functionTexts in body.
     */
    @GetMapping("/function-texts")
    public List<FunctionText> getAllFunctionTexts() {
        log.debug("REST request to get all FunctionTexts");
        return functionTextRepository.findAll();
    }

    /**
     * {@code GET  /function-texts/:id} : get the "id" functionText.
     *
     * @param id the id of the functionText to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functionText, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/function-texts/{id}")
    public ResponseEntity<FunctionText> getFunctionText(@PathVariable Long id) {
        log.debug("REST request to get FunctionText : {}", id);
        Optional<FunctionText> functionText = functionTextRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(functionText);
    }

    /**
     * {@code DELETE  /function-texts/:id} : delete the "id" functionText.
     *
     * @param id the id of the functionText to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/function-texts/{id}")
    public ResponseEntity<Void> deleteFunctionText(@PathVariable Long id) {
        log.debug("REST request to delete FunctionText : {}", id);
        functionTextRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
