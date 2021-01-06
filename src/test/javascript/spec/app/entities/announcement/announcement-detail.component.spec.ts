import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ProjecunitmangmentTestModule } from '../../../test.module';
import { AnnouncementDetailComponent } from 'app/entities/announcement/announcement-detail.component';
import { Announcement } from 'app/shared/model/announcement.model';

describe('Component Tests', () => {
  describe('Announcement Management Detail Component', () => {
    let comp: AnnouncementDetailComponent;
    let fixture: ComponentFixture<AnnouncementDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ announcement: new Announcement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjecunitmangmentTestModule],
        declarations: [AnnouncementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnnouncementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnnouncementDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load announcement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.announcement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
