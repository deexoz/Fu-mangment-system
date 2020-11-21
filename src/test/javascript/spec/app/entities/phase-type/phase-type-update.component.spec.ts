import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PumsTestModule } from '../../../test.module';
import { PhaseTypeUpdateComponent } from 'app/entities/phase-type/phase-type-update.component';
import { PhaseTypeService } from 'app/entities/phase-type/phase-type.service';
import { PhaseType } from 'app/shared/model/phase-type.model';

describe('Component Tests', () => {
  describe('PhaseType Management Update Component', () => {
    let comp: PhaseTypeUpdateComponent;
    let fixture: ComponentFixture<PhaseTypeUpdateComponent>;
    let service: PhaseTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [PhaseTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PhaseTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhaseTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhaseTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PhaseType(123);
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
        const entity = new PhaseType();
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
