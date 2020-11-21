import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PumsTestModule } from '../../../test.module';
import { PhaseTypeDetailComponent } from 'app/entities/phase-type/phase-type-detail.component';
import { PhaseType } from 'app/shared/model/phase-type.model';

describe('Component Tests', () => {
  describe('PhaseType Management Detail Component', () => {
    let comp: PhaseTypeDetailComponent;
    let fixture: ComponentFixture<PhaseTypeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ phaseType: new PhaseType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [PhaseTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PhaseTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhaseTypeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load phaseType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phaseType).toEqual(jasmine.objectContaining({ id: 123 }));
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
