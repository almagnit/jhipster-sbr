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
import sbr.converter.domain.Route;
import sbr.converter.repository.RouteRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.Route}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(RouteResource.class);

    private static final String ENTITY_NAME = "route";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RouteRepository routeRepository;

    public RouteResource(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * {@code POST  /routes} : Create a new route.
     *
     * @param route the route to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new route, or with status {@code 400 (Bad Request)} if the route has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/routes")
    public ResponseEntity<Route> createRoute(@RequestBody Route route) throws URISyntaxException {
        log.debug("REST request to save Route : {}", route);
        if (route.getId() != null) {
            throw new BadRequestAlertException("A new route cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Route result = routeRepository.save(route);
        return ResponseEntity
            .created(new URI("/api/routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /routes/:id} : Updates an existing route.
     *
     * @param id the id of the route to save.
     * @param route the route to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated route,
     * or with status {@code 400 (Bad Request)} if the route is not valid,
     * or with status {@code 500 (Internal Server Error)} if the route couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/routes/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable(value = "id", required = false) final Long id, @RequestBody Route route)
        throws URISyntaxException {
        log.debug("REST request to update Route : {}, {}", id, route);
        if (route.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, route.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!routeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Route result = routeRepository.save(route);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, route.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /routes/:id} : Partial updates given fields of an existing route, field will ignore if it is null
     *
     * @param id the id of the route to save.
     * @param route the route to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated route,
     * or with status {@code 400 (Bad Request)} if the route is not valid,
     * or with status {@code 404 (Not Found)} if the route is not found,
     * or with status {@code 500 (Internal Server Error)} if the route couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/routes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Route> partialUpdateRoute(@PathVariable(value = "id", required = false) final Long id, @RequestBody Route route)
        throws URISyntaxException {
        log.debug("REST request to partial update Route partially : {}, {}", id, route);
        if (route.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, route.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!routeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Route> result = routeRepository
            .findById(route.getId())
            .map(existingRoute -> {
                if (route.getOrt() != null) {
                    existingRoute.setOrt(route.getOrt());
                }
                if (route.getEntfernung() != null) {
                    existingRoute.setEntfernung(route.getEntfernung());
                }
                if (route.getHaltegrund() != null) {
                    existingRoute.setHaltegrund(route.getHaltegrund());
                }
                if (route.getGleis() != null) {
                    existingRoute.setGleis(route.getGleis());
                }
                if (route.getAusstieg() != null) {
                    existingRoute.setAusstieg(route.getAusstieg());
                }
                if (route.getGps() != null) {
                    existingRoute.setGps(route.getGps());
                }
                if (route.getAnsage() != null) {
                    existingRoute.setAnsage(route.getAnsage());
                }
                if (route.getEndAnsage() != null) {
                    existingRoute.setEndAnsage(route.getEndAnsage());
                }
                if (route.getStartAnsage() != null) {
                    existingRoute.setStartAnsage(route.getStartAnsage());
                }
                if (route.getViaAnsage() != null) {
                    existingRoute.setViaAnsage(route.getViaAnsage());
                }
                if (route.getStopAnsage() != null) {
                    existingRoute.setStopAnsage(route.getStopAnsage());
                }
                if (route.getStopAnsageMode() != null) {
                    existingRoute.setStopAnsageMode(route.getStopAnsageMode());
                }
                if (route.getZugziel() != null) {
                    existingRoute.setZugziel(route.getZugziel());
                }
                if (route.getEntwerter1() != null) {
                    existingRoute.setEntwerter1(route.getEntwerter1());
                }
                if (route.getEntwerter2() != null) {
                    existingRoute.setEntwerter2(route.getEntwerter2());
                }
                if (route.getZoneninfo() != null) {
                    existingRoute.setZoneninfo(route.getZoneninfo());
                }
                if (route.getAutomat10() != null) {
                    existingRoute.setAutomat10(route.getAutomat10());
                }
                if (route.getAttribute() != null) {
                    existingRoute.setAttribute(route.getAttribute());
                }
                if (route.getSprache1() != null) {
                    existingRoute.setSprache1(route.getSprache1());
                }
                if (route.getSprache2() != null) {
                    existingRoute.setSprache2(route.getSprache2());
                }
                if (route.getSprache3() != null) {
                    existingRoute.setSprache3(route.getSprache3());
                }
                if (route.getKurs() != null) {
                    existingRoute.setKurs(route.getKurs());
                }
                if (route.getZieltexte() != null) {
                    existingRoute.setZieltexte(route.getZieltexte());
                }
                if (route.getLiniennummer() != null) {
                    existingRoute.setLiniennummer(route.getLiniennummer());
                }
                if (route.getGattung() != null) {
                    existingRoute.setGattung(route.getGattung());
                }
                if (route.getLineMarker() != null) {
                    existingRoute.setLineMarker(route.getLineMarker());
                }
                if (route.getDs010() != null) {
                    existingRoute.setDs010(route.getDs010());
                }
                if (route.getLinienansage() != null) {
                    existingRoute.setLinienansage(route.getLinienansage());
                }
                if (route.getSpurkranz() != null) {
                    existingRoute.setSpurkranz(route.getSpurkranz());
                }
                if (route.getSkDauer() != null) {
                    existingRoute.setSkDauer(route.getSkDauer());
                }
                if (route.getSkBoogie() != null) {
                    existingRoute.setSkBoogie(route.getSkBoogie());
                }
                if (route.getPrmTur() != null) {
                    existingRoute.setPrmTur(route.getPrmTur());
                }
                if (route.getTursperrung() != null) {
                    existingRoute.setTursperrung(route.getTursperrung());
                }

                return existingRoute;
            })
            .map(routeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, route.getId().toString())
        );
    }

    /**
     * {@code GET  /routes} : get all the routes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of routes in body.
     */
    @GetMapping("/routes")
    public List<Route> getAllRoutes() {
        log.debug("REST request to get all Routes");
        return routeRepository.findAll();
    }

    /**
     * {@code GET  /routes/:id} : get the "id" route.
     *
     * @param id the id of the route to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the route, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/routes/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        log.debug("REST request to get Route : {}", id);
        Optional<Route> route = routeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(route);
    }

    /**
     * {@code DELETE  /routes/:id} : delete the "id" route.
     *
     * @param id the id of the route to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        log.debug("REST request to delete Route : {}", id);
        routeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
