import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PumsTestModule } from '../../../test.module';
import { ObservationDetailComponent } from 'app/entities/observation/observation-detail.component';
import { Observation } from 'app/shared/model/observation.model';

describe('Component Tests', () => {
  describe('Observation Management Detail Component', () => {
    let comp: ObservationDetailComponent;
    let fixture: ComponentFixture<ObservationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ observation: new Observation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [ObservationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ObservationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObservationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load observation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.observation).toEqual(jasmine.objectContaining({ id: 123 }));
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
