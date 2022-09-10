package fan.company.springbootjwtrealprojectuserindb.controller;

import fan.company.springbootjwtrealprojectuserindb.entity.Oylik;
import fan.company.springbootjwtrealprojectuserindb.entity.OylikPay;
import fan.company.springbootjwtrealprojectuserindb.entity.Tasks;
import fan.company.springbootjwtrealprojectuserindb.entity.TimeDto;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.OylikDto;
import fan.company.springbootjwtrealprojectuserindb.payload.OylikPayDto;
import fan.company.springbootjwtrealprojectuserindb.payload.RegisterDto;
import fan.company.springbootjwtrealprojectuserindb.service.OylikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/oylik")
public class OylikController {

    @Autowired
    OylikService service;

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR')")
    @PostMapping("/oylik_belgilash")
    public HttpEntity<?> oylikBelgilash(@Valid @RequestBody OylikDto dto){
        ApiResult apiResult = service.oylikBelgilash(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR')")
    @PutMapping("/oylikniTaxrirlash")
    public HttpEntity<?> oylikniTaxrirlash(@RequestBody OylikDto dto){
        ApiResult apiResult = service.oylikniTaxrirlash(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOylik (@PathVariable Long id){
        ApiResult apiResult = service.deleteOylik(id);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @GetMapping("/getalloylik")
    public ResponseEntity<?> getAllOylik(@RequestParam Integer page) {
        Page<Oylik> all = service.getAllOylik(page);
        return ResponseEntity.status(all.hasContent() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @GetMapping("/getalltolanganlar")
    public ResponseEntity<?> getAllTolanganlar(@RequestParam Integer page) {
        Page<OylikPay> all = service.getAllTolanganlar(page);
        return ResponseEntity.status(all.hasContent() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @GetMapping("/getbyuserid/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable Long id) {
        List<OylikPay> all = service.getByUserId(id);
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @GetMapping("/getbybetweentime")
    public ResponseEntity<?> getByBetweenTime(@RequestBody TimeDto timeDto) {
        List<OylikPay> all = service.getByBetweenTime(timeDto.getStart(), timeDto.getEnd());
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @GetMapping("/getByBetweenTimeAndUserId")
    public ResponseEntity<?> getByBetweenTimeAndUserId(@RequestBody TimeDto timeDto) {
        List<OylikPay> all = service.getByBetweenTimeAndUserId(timeDto.getStart(), timeDto.getEnd(), timeDto.getUserId());
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @PostMapping("/payOylik")
    public HttpEntity<?> payOylik(@RequestBody OylikPayDto dto){
        ApiResult apiResult = service.payOylik(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResult);
    }










}
