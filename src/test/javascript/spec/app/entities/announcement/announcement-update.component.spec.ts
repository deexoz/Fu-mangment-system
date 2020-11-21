import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PumsTestModule } from '../../../test.module';
import { AnnouncementUpdateComponent } from 'app/entities/announcement/announcement-update.component';
import { AnnouncementService } from 'app/entities/announcement/announcement.service';
import { Announcement } from 'app/shared/model/announcement.model';

describe('Component Tests', () => {
  describe('Announcement Management Update Component', () => {
    let comp: AnnouncementUpdateComponent;
    let fixture: ComponentFixture<AnnouncementUpdateComponent>;
    let service: AnnouncementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [AnnouncementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnnouncementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnnouncementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnnouncementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Announcement(123);
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
        const entity = new Announcement();
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
