import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PumsTestModule } from '../../../test.module';
import { PhaseUpdateComponent } from 'app/entities/phase/phase-update.component';
import { PhaseService } from 'app/entities/phase/phase.service';
import { Phase } from 'app/shared/model/phase.model';

describe('Component Tests', () => {
  describe('Phase Management Update Component', () => {
    let comp: PhaseUpdateComponent;
    let fixture: ComponentFixture<PhaseUpdateComponent>;
    let service: PhaseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [PhaseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PhaseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhaseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhaseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Phase(123);
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
        const entity = new Phase();
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
