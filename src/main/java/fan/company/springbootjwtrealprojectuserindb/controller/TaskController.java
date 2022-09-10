package fan.company.springbootjwtrealprojectuserindb.controller;

import fan.company.springbootjwtrealprojectuserindb.entity.Tasks;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.TaskDto;
import fan.company.springbootjwtrealprojectuserindb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER', 'ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> add (@Valid @RequestBody TaskDto dto){
        ApiResult apiResult = taskService.add(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER', 'ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit (@Valid @PathVariable Long id, @RequestBody TaskDto dto){
        ApiResult apiResult = taskService.edit(id, dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER', 'ROLE_MANAGER', 'ROLE_XODIM')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne (@PathVariable Long id){
        Tasks tasks = taskService.getOne(id);
        return ResponseEntity.status(tasks != null ? HttpStatus.OK:HttpStatus.CONFLICT).body(tasks);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page) {
        Page<Tasks> all = taskService.getAll(page);
        return ResponseEntity.status(all.hasContent() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER', 'ROLE_MANAGER', 'ROLE_XODIM')")
    @GetMapping("/getmytasks")
    public ResponseEntity<?> getAll() {
        List<Tasks> all = taskService.getAllMyTask();
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER', 'ROLE_MANAGER')")
    @GetMapping("/getAllByUser/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long id) {
        List<Tasks> all = taskService.getAllByUser(id);
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete (@PathVariable Long id){
        ApiResult apiResult = taskService.delete(id);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER','ROLE_MANAGER')")
    @GetMapping("/getMuddatiOtibKetganVazifalar")
    public ResponseEntity<?> getMuddatiOtibKetganVazifalar() {
        List<Tasks> all = taskService.getMuddatiOtibKetganVazifalar();
        return ResponseEntity.status(!all.isEmpty() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    /**
     * javax.validation ga o'zbekcha habar yuvorish uchun kerak
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }





}
