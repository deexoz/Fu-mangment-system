import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PumsTestModule } from '../../../test.module';
import { ResultDataUpdateComponent } from 'app/entities/result-data/result-data-update.component';
import { ResultDataService } from 'app/entities/result-data/result-data.service';
import { ResultData } from 'app/shared/model/result-data.model';

describe('Component Tests', () => {
  describe('ResultData Management Update Component', () => {
    let comp: ResultDataUpdateComponent;
    let fixture: ComponentFixture<ResultDataUpdateComponent>;
    let service: ResultDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [ResultDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ResultDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultData(123);
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
        const entity = new ResultData();
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
