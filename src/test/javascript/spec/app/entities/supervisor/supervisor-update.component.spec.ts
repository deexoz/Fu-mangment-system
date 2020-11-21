import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PumsTestModule } from '../../../test.module';
import { SupervisorUpdateComponent } from 'app/entities/supervisor/supervisor-update.component';
import { SupervisorService } from 'app/entities/supervisor/supervisor.service';
import { Supervisor } from 'app/shared/model/supervisor.model';

describe('Component Tests', () => {
  describe('Supervisor Management Update Component', () => {
    let comp: SupervisorUpdateComponent;
    let fixture: ComponentFixture<SupervisorUpdateComponent>;
    let service: SupervisorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [SupervisorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SupervisorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupervisorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupervisorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Supervisor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Supervisor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
